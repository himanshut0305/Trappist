package in.co.qedtech.trappist.payload;

public class GetSchoolsRequest {
    private int boardId;
    private int groupId;

    public GetSchoolsRequest(int boardId, int groupId) {
        this.boardId = boardId;
        this.groupId = groupId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
