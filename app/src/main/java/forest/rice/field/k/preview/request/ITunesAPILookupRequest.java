package forest.rice.field.k.preview.request;

public class ITunesAPILookupRequest extends  ITunesApiSearchRequest {

    public ITunesAPILookupRequest(String keyword) {
        super(keyword);
        this.endpoint = "https://itunes.apple.com/lookup?country=jp&media=music&entity=song&lang=ja_jp&limit=200&id=%s";
    }
}
