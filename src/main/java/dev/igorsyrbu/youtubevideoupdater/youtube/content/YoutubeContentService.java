package dev.igorsyrbu.youtubevideoupdater.youtube.content;

import dev.igorsyrbu.youtubevideoupdater.youtube.WithBearer;

public interface YoutubeContentService extends WithBearer {

	YoutubeVideo getVideo(String videoId, String accessToken);

	YoutubeVideo updateVideo(YoutubeVideo video, String token);

	void setThumbnail(String videoId, byte[] content, String token);
}
