package com.mcnc.webadmin.common.util;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * String
 * BigDecimal
 * Long
 * Integer
 * Boolean
 * 
 * @author Kevin
 *
 */
public class MData extends LinkedHashMap<String, Object> implements MDataProtocol {
	
	private static final long serialVersionUID = -5861114305569703387L;
	
	private boolean nullToInitialize = false;

	public boolean isNullToInitialize() {
		return this.nullToInitialize;
	}
	
	public void setNullToInitialize( boolean nullToInitialize ) {
		this.nullToInitialize = nullToInitialize;
	}
	
	public MData() {
		super();
	}
	
	public MData( Map<String, Object> map ) {
		super( map );
	}
	
	public String getString( String key ) {
		if ( get( key ) != null ) {
			return String.valueOf( get( key ) );
		}
		return null;
	}
	
	public BigDecimal getBigDecimal( String key ) {
		if ( get( key ) != null ) {
			return new BigDecimal( getString( key ) );
		}
		return BigDecimal.ZERO;
	}
	
	public long getLong( String key ) {
		if ( get( key ) != null ) {
			return Long.valueOf( getString( key ) ).longValue();
		}
		return 0L;
	}
	
	public int getInt( String key ) {
		if ( get( key ) != null ) {
			return Integer.valueOf( getString( key ) ).intValue();
		}
		return 0;
	}
	
	public Boolean getBoolean( String key ) {
		if ( get( key ) != null ) {
			return Boolean.valueOf( getString( key ) ).booleanValue();
		}
		return null;
	}
	
	public short getShort( String key ) {
		if ( get( key ) != null ) {
			return Short.valueOf( getString( key ) ).shortValue();
		}
		return 0;
	}
	
	public double getDouble( String key ) {
		if ( get( key ) != null ) {
			return Double.valueOf( getString( key ) ).doubleValue();
		}
		return 0.0D;
	}
	
	public float getFloat( String key ) {
		if ( get( key ) != null ) {
			return Float.valueOf( getString( key ) ).floatValue();
		}
		return 0.0F;
	}
	
	public MData getMData( String key ) {
		try {
			if ( get( key ) != null ) {
				return (MData) get( key );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return new MData();
	}
	
	public void set( String key, Object value ) {
		this.put( key, value );
	}
	
	public void setString( String key, String value ) {
		this.put( key, value );
	}
	
	public void setBigDecimal( String key, BigDecimal value ) {
		this.put( key, value );
	}
	
	public void setLong( String key, long value ) {
		this.put( key, value );
	}
	
	public void setInt( String key, int value ) {
		this.put( key, value );
	}

	public void setBoolean( String key, boolean value ) {
		this.put( key, value );
	}
	
	public void setShort( String key, short value ) {
		this.put( key, value );
	}

	public void setDouble( String key, double value ) {
		this.put( key, value );
	}

	public void setFloat( String key, float value ) {
		this.put( key, value );
	}
	
	public void setMData( String key, MData value ) {
		this.put( key, value );
	}
	
	public void setMMultiData( String key, MMultiData value ) {
		this.put( key, value );
	}
	
	@SuppressWarnings( "unchecked" )
	public MMultiData getMMultiData( String key ) {
		try {
			if ( get( key ) != null ) {
				return new MMultiData((List<LinkedHashMap<String, Object>>) get( key ));
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return new MMultiData();
	}
	
	public void appendFrom( MData data ) {
		this.putAll( data );
	}
}
