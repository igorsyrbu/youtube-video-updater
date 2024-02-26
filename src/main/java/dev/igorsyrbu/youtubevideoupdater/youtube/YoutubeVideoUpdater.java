package dev.igorsyrbu.youtubevideoupdater.youtube;

import dev.igorsyrbu.youtubevideoupdater.image.ImageGenerator;
import dev.igorsyrbu.youtubevideoupdater.youtube.auth.YoutubeAuthService;
import dev.igorsyrbu.youtubevideoupdater.youtube.config.YoutubeProperties;
import dev.igorsyrbu.youtubevideoupdater.youtube.content.VideoStatistics;
import dev.igorsyrbu.youtubevideoupdater.youtube.content.YoutubeContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Component;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

@Log4j2
@Component
@RequiredArgsConstructor
public class YoutubeVideoUpdater implements Runnable {

	private static final String TITLE_TEMPLATE = "This video has %s views, %s likes and %s comments";

	private final ImageGenerator imageGenerator;
	private final YoutubeAuthService authService;
	private final YoutubeContentService contentService;
	private final YoutubeProperties properties;

	@Override
	public void run() {
		val token = authService.refreshAccessToken();
		val videoId = properties.videoId();
		val video = contentService.getVideo(videoId, token);
		val snippet = video.getSnippet();
		val statistics = video.getStatistics();
		val oldTitle = snippet.getTitle();
		val newTitle = buildTitle(statistics);

		if (newTitle.equals(oldTitle)) {
			log.info("Skip updating, counters are up to date: {}", statistics);
			return;
		}

		val image = generateImage(statistics);
		snippet.setTitle(newTitle);

		contentService.updateVideo(video, token);
		contentService.setThumbnail(videoId, image, token);
	}

	private String buildTitle(VideoStatistics statistics) {
		val viewCount = statistics.getViewCount();
		val likeCount = statistics.getLikeCount();
		val commentCount = statistics.getCommentCount();

		return format(TITLE_TEMPLATE,
					  getFormattedNumber(viewCount),
					  getFormattedNumber(likeCount),
					  getFormattedNumber(commentCount));
	}

	private byte[] generateImage(VideoStatistics statistics) {
		val viewCount = statistics.getViewCount();
		val formatted = getFormattedNumber(viewCount);

		return imageGenerator.apply(formatted);
	}

	private String getFormattedNumber(String value) {
		val number = parseInt(value);

		return format("%,d", number);
	}

}
