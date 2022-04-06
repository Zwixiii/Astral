package moonlight.gui.window;

public class Window {
	
	private String name;
	
	private int x, y, width, height, draggingX, draggingY, draggingWidth, draggingHeight;
	
	private boolean drag, dragging, showWindow;
	
	public Window(String name, int x, int y, int width, int height, boolean drag, boolean showWindow) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = x + width;
		this.height = y + height;
		this.drag = drag;
		this.dragging = false;
		this.showWindow = showWindow;
	}
	
	public void onRender() {}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDraggingX() {
		return draggingX;
	}

	public void setDraggingX(int draggingX) {
		this.draggingX = draggingX;
	}

	public int getDraggingY() {
		return draggingY;
	}

	public void setDraggingY(int draggingY) {
		this.draggingY = draggingY;
	}

	public int getDraggingWidth() {
		return draggingWidth;
	}

	public void setDraggingWidth(int draggingWidth) {
		this.draggingWidth = draggingWidth;
	}

	public int getDraggingHeight() {
		return draggingHeight;
	}

	public void setDraggingHeight(int draggingHeight) {
		this.draggingHeight = draggingHeight;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	public boolean isDragging() {
		return dragging;
	}

	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}

	public boolean isShowWindow() {
		return showWindow;
	}

	public void setShowWindow(boolean showWindow) {
		this.showWindow = showWindow;
	}
}
