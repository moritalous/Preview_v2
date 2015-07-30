package forest.rice.field.k.preview.request;

public class ITunesAPILookupCollectionRequest extends  ITunesApiSearchRequest {

    public ITunesAPILookupCollectionRequest(String keyword) {
        super(keyword);
        this.endpoint = "https://itunes.apple.com/lookup?id=%s%s&entity=album&sort=recent&limit=200";
    }
}
