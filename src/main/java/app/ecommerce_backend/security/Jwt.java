package app.ecommerce_backend.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class Jwt {
	
	public String generateToken(UserPrincipal userPrincipal) {
		String[] claims = getClaimsFromUser(userPrincipal);
		return JWT.create()
				.withIssuedAt(new Date())
				.withSubject(userPrincipal.getUsername())
				.withArrayClaim("Authorities",claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + 432000000))
				.sign(Algorithm.HMAC512("secret".getBytes()));
				
	}
	public List<GrantedAuthority> getAuthorities(String token) {
		String[] claims = getClaimsFromToken(token);
		return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
	
	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities,
    		HttpServletRequest request) {
    	UsernamePasswordAuthenticationToken userPasswordToken = new 
    			UsernamePasswordAuthenticationToken(username, null, authorities);
    	userPasswordToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    	return userPasswordToken;
    }
	public boolean isTokenValid(String username, String token) {
    	JWTVerifier verifier = getJWTVerifier();
    	return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
    }
	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		System.out.println(verifier.verify(token).getSubject());
		System.out.println(verifier.verify(token).getSignature());
		return verifier.verify(token).getSubject();
	}
	
	private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
		List<String> authorities = new ArrayList<String>();
		for(GrantedAuthority ga : userPrincipal.getAuthorities()) {
			authorities.add(ga.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}
	
	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getClaim("Authorities").asArray(String.class);
	}
	
	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		try {
			Algorithm algorithm =  Algorithm.HMAC512("secret");
			verifier = JWT.require(algorithm).build();
		} catch(JWTVerificationException e) {
			throw new JWTVerificationException("Token can not be verified");
		}
		return verifier;
	}
	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}

}
