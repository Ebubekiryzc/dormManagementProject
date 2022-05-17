package tr.edu.duzce.mf.bm.core.utilities.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class TokenGenerator {
    public String generateToken(List<OperationClaim> roles, int userId) {
        String token = "";
        String subject = "appuser";
        String secret = getSecretKey();
        String id = getId();
        String issuer = getIssuer();
        long timeToLiveMilliseconds = getTimeToLive();


        // Signature algoritmasını tanıtma
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // Token expiry için süre
        long currentTimeMilliseconds = System.currentTimeMillis();
        Date now = new Date(currentTimeMilliseconds);

        // Api şifresi
        byte[] apiSecretKeyBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiSecretKeyBytes, signatureAlgorithm.getJcaName());

        // JWT
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setIssuer(issuer)
                .setSubject(subject)
                .setId(id)
                .claim("roles", roles.stream().map(operationClaim -> operationClaim.getName()).collect(Collectors.joining(",")))
                .claim("userId", userId)
                .signWith(signatureAlgorithm, signingKey);

        if (timeToLiveMilliseconds >= 0) {
            long expirationMillis = currentTimeMilliseconds + timeToLiveMilliseconds;
            Date expire = new Date(expirationMillis);
            builder.setExpiration(expire);
        }

        token = builder.compact();
        return token;
    }

    private long getTimeToLive() {
        return 3600000; // 1 saat
    }

    public Boolean isValid(String accessToken, Set<String> roleSet) {
        Boolean result = false;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(getSecretKey()))
                    .parseClaimsJws(accessToken).getBody();

            // Rolleri çıkaralım:
            String roles = claims.get("roles", String.class);
            Set<String> claimRoles = new HashSet<String>(Arrays.asList(roles.split(",")));
            roleSet.retainAll(claimRoles);

            if (claims.getId().equals(getId()) && claims.getIssuer().equals(getIssuer()) && roleSet.size() >= 1)
                result = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    private String getIssuer() {
        return "duzceuniversity";
    }

    private String getId() {
        return "duzce.mf.bm";
    }

    private String getSecretKey() {
        return "mysupersecretkey";
    }
}
