package in.co.qedtech.trappist.payload;

import in.co.qedtech.trappist.model.School;
import in.co.qedtech.trappist.model.SchoolTheme;

public class SaveSchoolRequest {
    private int boardId;
    private int schoolChainId;

    private String name;
    private String alias;
    private String code;

    private String theme;
    private String logo;

    public SaveSchoolRequest(int boardId, int schoolChainId, String name, String alias, String code, String theme, String logo) {
        this.boardId = boardId;
        this.schoolChainId = schoolChainId;
        this.name = name;
        this.alias = alias;
        this.code = code;
        this.theme = theme;
        this.logo = logo;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getSchoolChainId() {
        return schoolChainId;
    }

    public void setSchoolChainId(int schoolChainId) {
        this.schoolChainId = schoolChainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SchoolTheme getTheme() {
        if(this.theme.equals("RED")) {
            return SchoolTheme.RED;
        }
        else if(this.theme.equals("BLUE")) {
            return SchoolTheme.BLUE;
        }
        else if(this.theme.equals("GREEN")) {
            return SchoolTheme.GREEN;
        }
        else if(this.theme.equals("ORANGE")) {
            return SchoolTheme.ORANGE;
        }
        else if(this.theme.equals("INDIGO")) {
            return SchoolTheme.INDIGO;
        }
        else {
            return SchoolTheme.RED;
        }
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "SaveSchoolRequest{" +
                "boardId=" + boardId +
                ", schoolChainId=" + schoolChainId +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", code='" + code + '\'' +
                ", theme='" + theme + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
