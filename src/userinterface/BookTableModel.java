// tabs=4
//************************************************************
//	COPYRIGHT 2021, Kyle D. Adams, Matthew E. Morgan and
//   Sandeep Mitra, State University of New York. - Brockport
//   (SUNY Brockport)
//	ALL RIGHTS RESERVED
//
// This file is the product of SUNY Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of SUNY Brockport.
//************************************************************
//
// specify the package

package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class BookTableModel {
    private final SimpleStringProperty barcode;
    private final SimpleStringProperty title;
    private final SimpleStringProperty author1;

    //----------------------------------------------------------------------------
    public BookTableModel(Vector<String> bookData) {
        barcode =  new SimpleStringProperty(bookData.elementAt(0));
        title =  new SimpleStringProperty(bookData.elementAt(1));
        author1 =  new SimpleStringProperty(bookData.elementAt(2));
    }

    //----------------------------------------------------------------------------
    public String getBarcode() {
        return barcode.get();
    }

    //----------------------------------------------------------------------------
    public void setBarcode(String idv) {
        barcode.set(idv);
    }

    //----------------------------------------------------------------------------
    public String getTitle() {
        return title.get();
    }

    //----------------------------------------------------------------------------
    public void setTitle(String nm) {
        title.set(nm);
    }

    //----------------------------------------------------------------------------
    public String getAuthor1() {
        return author1.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor1(String yr) {
        author1.set(yr);
    }

}
