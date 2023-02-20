/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LIMEXC
 * @since 2022-05-13
 **/
@Data
public class HitokotoVO implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 唯一ID
     */
    private String uuid;

    /**
     * 内容
     */
    private String hitokoto;

    /**
     * 类型
     */
    private String type;

    /**
     * 出处
     */
    private String from;

    /**
     * 作者
     */
    private String fromWho;

    /**
     * 添加者
     */
    private String creator;

    /**
     * 添加者ID
     */
    private String creatorUid;

    /**
     * 审核人
     */
    private String reviewer;

    /**
     * 提交方式
     */
    private String commitFrom;

    /**
     * 创建时间
     */
    private Long createdAt;

    /**
     * 内容长度
     */
    private Long length;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        HitokotoVO other = (HitokotoVO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
                && (this.getHitokoto() == null ? other.getHitokoto() == null : this.getHitokoto().equals(other.getHitokoto()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getFrom() == null ? other.getFrom() == null : this.getFrom().equals(other.getFrom()))
                && (this.getFromWho() == null ? other.getFromWho() == null : this.getFromWho().equals(other.getFromWho()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getCreatorUid() == null ? other.getCreatorUid() == null : this.getCreatorUid().equals(other.getCreatorUid()))
                && (this.getReviewer() == null ? other.getReviewer() == null : this.getReviewer().equals(other.getReviewer()))
                && (this.getCommitFrom() == null ? other.getCommitFrom() == null : this.getCommitFrom().equals(other.getCommitFrom()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
                && (this.getLength() == null ? other.getLength() == null : this.getLength().equals(other.getLength()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getHitokoto() == null) ? 0 : getHitokoto().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getFrom() == null) ? 0 : getFrom().hashCode());
        result = prime * result + ((getFromWho() == null) ? 0 : getFromWho().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreatorUid() == null) ? 0 : getCreatorUid().hashCode());
        result = prime * result + ((getReviewer() == null) ? 0 : getReviewer().hashCode());
        result = prime * result + ((getCommitFrom() == null) ? 0 : getCommitFrom().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getLength() == null) ? 0 : getLength().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uuid=").append(uuid);
        sb.append(", hitokoto=").append(hitokoto);
        sb.append(", type=").append(type);
        sb.append(", from=").append(from);
        sb.append(", fromWho=").append(fromWho);
        sb.append(", creator=").append(creator);
        sb.append(", creatorUid=").append(creatorUid);
        sb.append(", reviewer=").append(reviewer);
        sb.append(", commitFrom=").append(commitFrom);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", length=").append(length);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
