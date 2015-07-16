/**
 * 
 */
package pfa.alliance.fim.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * This is a base class for localized models.
 * 
 * @author Csaba
 */
@MappedSuperclass
public abstract class GenericLocalizedModel<ID extends Serializable, T extends Identifiable<ID>>
    extends GenericModel
    implements Identifiable<Long>
{
    private static final long serialVersionUID = -4199253986034109498L;

    /** The localization record id. */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long id;

    /** The locale in what language is the text written. */
    @Column( name = "locale", nullable = false, length = 6 )
    private String locale;

    /** The text in the given locale. */
    @Column( name = "text_value", nullable = false, length = 100 )
    private String value;

    /** The record that is localized. */
    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "record_id" )
    private T record;

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public void setId( Long id )
    {
        this.id = id;
    }

    public String getLocale()
    {
        return locale;
    }

    public void setLocale( String locale )
    {
        this.locale = locale;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public T getRecord()
    {
        return record;
    }

    public void setRecord( T record )
    {
        this.record = record;
    }

    /**
     * Gets the record ID.
     * 
     * @return the ID of the record or null if record is not defined
     */
    protected ID getRecordId()
    {
        return ( record != null ) ? record.getId() : null;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( locale, getRecordId() );
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );
        tsb.append( "id", id );
        tsb.append( "locale", locale );
        tsb.append( "record_id", getRecordId() );
        tsb.append( "value", value );
        return tsb.toString();
    }

    /**
     * Helper method for equals().
     * 
     * @param obj the object to check
     * @return true if objects are equals
     */
    protected boolean equalsHelper( GenericLocalizedModel<ID, T> obj )
    {
        return Objects.equals( locale, obj.locale ) && Objects.equals( getRecordId(), obj.getRecordId() );
    }
}
