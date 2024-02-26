package dev.igorsyrbu.youtubevideoupdater.youtube.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class YoutubeVideos {

	@JsonProperty("kind")
	private String kind;

	@JsonProperty("items")
	private List<YoutubeVideo> items;
}