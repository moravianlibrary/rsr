package cz.muni.kisk.rsr.tokenizer;

public class StringToken {
	
	private String string;
	private int offset;
	private int length;
	private StringTokenType type;
	
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public StringTokenType getType() {
		return type;
	}
	public void setType(StringTokenType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "StringToken [string=" + string + ", offset=" + offset
				+ ", length=" + length + ", type=" + type + "]";
	}
}
