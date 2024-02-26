package dev.igorsyrbu.youtubevideoupdater.youtube;

public interface WithBearer {

	String BEARER = "Bearer ";

	default String toBearer(String token) {
		if (token.startsWith(BEARER)) {
			return token;
		}

		return BEARER + token;
	}

}
