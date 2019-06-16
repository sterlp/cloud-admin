package org.sterl.cloudadmin.common.id;

public class ETag extends AbstractId<Long> {
    private static final long serialVersionUID = -7396765197499474767L;
    public static ETag newEtag(Long value) {
        return value == null ? null : new ETag(value);
    }
    public ETag(Long value) {
        super(value);
    }
}
