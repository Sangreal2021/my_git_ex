package model;

public class Attaches {
	private int id;
    private String path;
    private int board_id;
    
    public Attaches() {}

	public Attaches(int id, String path, int board_id) {
		super();
		this.id = id;
		this.path = path;
		this.board_id = board_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
    
}
