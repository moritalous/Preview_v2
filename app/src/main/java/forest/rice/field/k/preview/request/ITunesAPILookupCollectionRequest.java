package forest.rice.field.k.preview.request;

public class ITunesAPILookupCollectionRequest extends  ITunesApiSearchRequest {

    public ITunesAPILookupCollectionRequest(String keyword) {
        super(keyword);
        this.endpoint = "https://itunes.apple.com/lookup?country=jp&media=music&entity=album&lang=ja_jp&limit=200&id=%s";
    }
}
