# youtube-video-updater
AWS Lambda function that auto-updates a YouTube video title and thumbnail based on the video views, likes and comments counters.

### Mandatory environment properties
```sh
youtube:
  clientId: REPLACE_WITH_YOUR_YOUTUBE_CLIENT_ID
  clientSecret: REPLACE_WITH_YOUR_YOUTUBE_CLIENT_SECRET
  refreshToken: REPLACE_WITH_YOUR_YOUTUBE_REFRESH_TOKEN
  videoId: REPLACE_WITH_YOUR_YOUTUBE_VIDEO_ID
```

### Build
``` sh
./gradlew clean build
```
The packages will be located in `./build/libs` directory

### Deployment
Upload jar with `aws` prefix to AWS Lambda function with Java 21 runtime

### Thumbnail
Replace thumbnail.png in [resources/static](src%2Fmain%2Fresources%2Fstatic) directory for using your thumbnail.

### Walkthrough
https://www.youtube.com/watch?v=Tn_tHlhkO1M
