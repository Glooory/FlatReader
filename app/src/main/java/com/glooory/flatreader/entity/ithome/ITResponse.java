package com.glooory.flatreader.entity.ithome;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Glooory on 2016/10/12 0012 12:23.
 */

@Root(name = "rss")
public class ITResponse {

    @Element(name = "channel")
    ITHomeChannelBean channel;
    @Attribute(name = "version")
    String version;

    public ITHomeChannelBean getChannel() {
        return channel;
    }

    public void setChannel(ITHomeChannelBean channel) {
        this.channel = channel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
