package pt.isel.ls.utils.http;

import pt.isel.ls.utils.common.Writable;

public interface HttpContent extends Writable {
    String getMediaType();
}
