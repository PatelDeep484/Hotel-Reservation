package application.classes;

public class room {
	private int roomId;
    private String roomType;
    private double roomPrice;

    public room(int roomId, String roomType, double roomPrice) {
    	this.roomId = roomId;
    	this.roomType = roomType;
    	this.roomPrice = roomPrice;
    }
	
    public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public double getRoomPrice() {
		return roomPrice;
	}
	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}
    
    
}
