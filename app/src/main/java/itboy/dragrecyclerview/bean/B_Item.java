package itboy.dragrecyclerview.bean;

public class B_Item implements Base_Item {

    private String title;
    private String content;
    private String icon;
    private int drawableIcon;
    private boolean isDrag = false;

    public B_Item(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(int drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    public static final int Item_Tpye = 1;

    @Override
    public int getItemType() {
        return Item_Tpye;
    }

    @Override
    public boolean isDrag() {
        return isDrag;
    }

    @Override
    public void setDrag(boolean isDrag) {
        this.isDrag = isDrag;
    }


}
