package htmlrenderer.Painting;

public class DisplayText implements DisplayCommand{
    private String text;
    public float x;
    public float y;
    public DisplayText(String text)
    {
        this.text = text;
    }
    public DisplayText(String text, float x, float y)
    {
        this.text = text;
        this.x = x;
        this.y = y;
    }
    public String getText()
    {
        return text;
    }

}
