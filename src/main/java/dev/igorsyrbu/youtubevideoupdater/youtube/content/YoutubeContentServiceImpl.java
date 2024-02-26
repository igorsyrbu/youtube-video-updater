package dev.igorsyrbu.youtubevideoupdater.youtube.content;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Log4j2
@Service
@RequiredArgsConstructor
public class YoutubeContentServiceImpl implements YoutubeContentService {

	public static final String ID = "id";
	public static final String SNIPPET = "snippet";
	public static final String STATISTICS = "statistics";
	public static final String DELIMITER = ",";
	public static final String UPLOAD_ID_HEADER = "x-guploader-uploadid";

	private final YoutubeContentFeignClient client;

	@Override
	public YoutubeVideo getVideo(String videoId, String token) {
		log.info("Getting video with id: {}", videoId);

		val bearer = toBearer(token);
		val part = join(DELIMITER, SNIPPET, STATISTICS);

		val info = client.getVideosInfo(videoId, part, bearer);
		val items = info.getItems();

		if (isEmpty(items)) {
			val message = format("Video with id %s not found", videoId);

			throw new IllegalArgumentException(message);
		}

		return items.getFirst();
	}

	@Override
	public YoutubeVideo updateVideo(YoutubeVideo video, String token) {
		log.info("Updating video with id: {}", video.getId());

		val bearer = toBearer(token);
		val part = join(DELIMITER, ID, SNIPPET);
		sanitize(video);

		return client.updateVideo(part, bearer, video);
	}

	@SneakyThrows
	@Override
	public void setThumbnail(String videoId, byte[] content, String token) {
		log.info("Setting thumbnail for video with id: {}", videoId);

		val bearer = toBearer(token);
		val uploadId = getUploadId(videoId, bearer);

		client.uploadThumbnail(bearer, videoId, uploadId, content);
	}

	private String getUploadId(String videoId, String bearer) {
		val response = client.getThumbnailUploadUrl(videoId, bearer);
		val headers = response.getHeaders();
		val uploadIdValues = headers.get(UPLOAD_ID_HEADER);

		if (isEmpty(uploadIdValues)) {
			val message = format("Upload id header is not present in the response headers: %s", headers);

			throw new IllegalStateException(message);
		}

		return uploadIdValues.getFirst();
	}

	private void sanitize(YoutubeVideo video){
		video.setStatistics(null);
	}

}
