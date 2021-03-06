package com.aaaaahhhhhhh.zenith.radio.util;

import java.nio.charset.Charset;

public class BinaryReader {
	protected int pos;
	protected byte[] data;
	
	public BinaryReader( byte[] buffer ) {
		this.pos = 0;
		this.data = buffer;
	}
	
	public int seek( int newPos ) {
		int oldPos = pos;
		pos = newPos;
		return oldPos;
	}
	
	public int pos() {
		return pos;
	}
	
	public int getInt8() {
		if ( pos >= data.length ) return 0;
		byte v = data[ pos++ ];
		if ( v < 0 ) {
			return 1 << 7 | ( v & 0b1111111 );
		}
		return v;
	}
	
	public int getInt16() {
		return getInt8() << 8 | getInt8() << 8;
	}
	
	public int getInt16Little() {
		return getInt8() | getInt8() << 8;
	}
	
	public int getInt32() {
		return ( ( getInt8() << 24 ) |
				( getInt8() << 16 ) |
				( getInt8() << 8 ) |
				( getInt8() ) );
	}
	
	public int getInt32Little() {
		return ( ( getInt8() ) |
				( getInt8() << 8 ) |
				( getInt8() << 16 ) |
				( getInt8() << 24 ) );
	}
	
	public long getLong64() {
		return ( ( ( ( long ) getInt8() ) << 56 ) |
				( ( ( long ) getInt8() ) << 48 ) |
				( ( ( long ) getInt8() ) << 40 ) |
				( ( ( long ) getInt8() )<< 32 ) |
				( ( ( long ) getInt8() ) << 24 ) |
				( ( ( long ) getInt8() ) << 16 ) |
				( ( ( long ) getInt8() ) << 8 ) |
				( ( ( long ) getInt8() ) ) );
	}
	
	public long getLong64Little() {
		return ( ( ( ( long ) getInt8() ) ) |
				( ( ( long ) getInt8() ) << 8 ) |
				( ( ( long ) getInt8() ) << 16 ) |
				( ( ( long ) getInt8() ) << 24 ) |
				( ( ( long ) getInt8() ) << 32 ) |
				( ( ( long ) getInt8() ) << 40 ) |
				( ( ( long ) getInt8() ) << 48 ) |
				( ( ( long ) getInt8() ) << 56 ) );
	}
	
	public double get2Dot14() {
		return ( ( double ) getInt16() ) / ( 1 << 14 );
	}
	
	public double getFixed() {
		return ( ( double ) getInt32() ) / ( 1 << 16 );
	}
	
	public String getString( int len ) {
		StringBuilder b = new StringBuilder();
		for ( int i = 0; i < len; i++ ) {
			b.append( ( char ) getInt8() );
		}
		return b.toString();
	}
	
	public String getString( int len, Charset set ) {
		byte[] buf = new byte[ len ];
		for ( int i = 0; i < len; i++ ) {
			buf[ i ] = ( byte ) getInt8();
		}
		return new String( buf, set );
	}
	
	public int length() {
		return data.length;
	}
}
