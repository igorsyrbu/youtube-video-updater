package dev.igorsyrbu.youtubevideoupdater.aws;

import dev.igorsyrbu.youtubevideoupdater.youtube.YoutubeVideoUpdater;
import java.util.function.UnaryOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.OK;

@Component
@RequiredArgsConstructor
public class LambdaFunction implements UnaryOperator<String> {

	private final YoutubeVideoUpdater youtubeVideoUpdater;

	@Override
	public String apply(String value) {
		youtubeVideoUpdater.run();

		return OK.toString();
	}
}
