package GUI;

import DTO.Prenotazione;
import DTO.Strumento;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {

    private JTable tableStrumenti;
    private JTable tablePrenotazioni;

    private JTable tablePrenStrumenti;
    private TableModelStrumento tableModelStrumento;

    private TableModelMiePrenotazioni tableModelMiePrenotazioni;

    private TableModelPrenStrum tableModelPrenStrum;

    private Integer codStrumentoAttuale = null;

    private PrenotazioneSelectionListener prenotazioneSelectionListener;

    //////////////////////////////////////TABLE PANEL PER STRUMENTI////////////////////////////////
    public TablePanel(List<Strumento> strumenti, NewBooking newBooking, BookingFrame bookingFrame) {

        //RAPPRESENTAZIONE TABELLA STRUMENTI DOPO LA RICERCA

        tableModelStrumento = new TableModelStrumento(strumenti);

        tableStrumenti = new JTable(tableModelStrumento);


        setLayout(new BorderLayout());
        add(new JScrollPane(tableStrumenti), BorderLayout.CENTER);

        tableStrumenti.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {


                //In questo modo impediamo all'utente di selezionare più righe alla volta evitando errori
                ListSelectionModel selectionModel = tableStrumenti.getSelectionModel();
                selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tableStrumenti.getSelectedRow();
                    if (selectedRow != -1) {

                        Strumento selectedStrumento = tableModelStrumento.getStrumentoAtRow(selectedRow);


                            codStrumentoAttuale = selectedStrumento.getCodStrumento();

                            //Con questo metodo passiamo lo strumento selezionato all'oggetto CalendarDialog
                            newBooking.setStrumSelezCalend(selectedStrumento);

                            //Passiamo lo Strumento alla classe BookingFrame quando l'utente prenota uno strumento
                            if(bookingFrame != null) {
                                bookingFrame.setStrumentoAttuale(selectedStrumento);
                            }
                            newBooking.setCodStrumentoBookingFrame(codStrumentoAttuale.toString());

                            newBooking.bookingButtonAvailability();
                            newBooking.bookingButtonCalendar();



                    } else {
                        newBooking.removeBookingButton();
                        newBooking.removeCalendarButton();
                    }
                }
            }
        });

    }

    //////////////////////////////TABLE PANEL PER LE PRENOTAZIONI DELL'UTENTE////////////////

    public TablePanel(List<Prenotazione> listaPrenotazioni, PrenotazioneSelectionListener listener) {

        this.prenotazioneSelectionListener = listener;

        tableModelMiePrenotazioni = new TableModelMiePrenotazioni(listaPrenotazioni);

        tablePrenotazioni = new JTable(tableModelMiePrenotazioni);

        tablePrenotazioni.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                //In questo modo impediamo all'utente di selezionare più righe alla volta evitando errori
                ListSelectionModel selectionModel = tablePrenotazioni.getSelectionModel();
                selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                //Prendiamo le informazioni della riga selezionata
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tablePrenotazioni.getSelectedRow();
                    if (selectedRow != -1) {

                        //Prendiamo la prenotazione della riga tramite il metodo di tableModelMiePrenotazioni
                        Prenotazione miaPrenotazSelez = tableModelMiePrenotazioni.getPrenotazioneAtRow(selectedRow);

                            //Chiamiamo il metodo dell'interfaccia e allo stesso tempo definiamo il valore della variabile
                            //presente in MyBooking così da inviare a quest'ultima le informazioni sulla prenotazione selezionata
                            prenotazioneSelectionListener.prenotazioneSelected(miaPrenotazSelez);

                    }

                    else {
                        //In questo modo quando non è selezionata nessuna riga non viene salvata l'ultima prneotazione e i pulsanti
                        //non sono disponibili e quindi non permettono di modificare o eliminare
                        prenotazioneSelectionListener.prenotazioneSelected(null);
                    }
                }
            }
        });



        setLayout(new BorderLayout());
        add(new JScrollPane(tablePrenotazioni), BorderLayout.CENTER);


    }

    //Creo un nuovo costruttore per il calendario, gli aggiungo il parametro String per differenziarlo
    //da quello per le prenotazioni dell'utente.

    //////////////////////////////TABLE PANEL PER IL CALENDARIO DEGLI STRUMENTI////////////////
    public TablePanel(List<Prenotazione> listaPrenotazioniStrumento, String calendario) {

       tableModelPrenStrum = new TableModelPrenStrum(listaPrenotazioniStrumento);

       tablePrenStrumenti = new JTable(tableModelPrenStrum);

        setLayout(new BorderLayout());
        add(new JScrollPane(tablePrenStrumenti), BorderLayout.CENTER);

    }


    public boolean isRowSelected() {

        return tableStrumenti.getSelectedRow() != -1;


    }



    public void setData(List<Strumento> listaStrumenti) {

        tableModelStrumento.setData(listaStrumenti);
        tableModelStrumento.fireTableDataChanged();

    }

    public void setDataMiePrenotazioni(List<Prenotazione> listaPrenotazioni) {

        tableModelMiePrenotazioni.setData(listaPrenotazioni);
        tableModelMiePrenotazioni.fireTableDataChanged();

    }

    public void aggiornaMiePrenotazioni() {

        tableModelMiePrenotazioni.fireTableDataChanged();

    }




    public void aggiorna() {

        tableModelStrumento.fireTableDataChanged();
        tableModelMiePrenotazioni.fireTableDataChanged();
    }
}
