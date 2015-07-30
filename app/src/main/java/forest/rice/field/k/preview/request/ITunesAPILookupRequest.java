package forest.rice.field.k.preview.request;

public class ITunesAPILookupRequest extends  ITunesApiSearchRequest {

    public ITunesAPILookupRequest(String keyword) {
        super(keyword);
        this.endpoint = "https://itunes.apple.com/lookup?&id=%s%s&media=music&entity=song&limit=200";
    }
}
