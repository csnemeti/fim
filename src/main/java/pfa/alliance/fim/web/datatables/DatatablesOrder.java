/**
 * 
 */
package pfa.alliance.fim.web.datatables;

import java.io.Serializable;

/**
 * This class defines the order for a datatables call.
 * 
 * @author Csaba
 */
public class DatatablesOrder
    implements Serializable
{
    private String dir;

    private Integer column;

    public String getDir()
    {
        return dir;
    }

    public void setDir( String dir )
    {
        this.dir = dir;
    }

    public Integer getColumn()
    {
        return column;
    }

    public void setColumn( Integer column )
    {
        this.column = column;
    }

    @Override
    public String toString()
    {
        return "ORDER BY " + column + " " + dir;
    }
}
