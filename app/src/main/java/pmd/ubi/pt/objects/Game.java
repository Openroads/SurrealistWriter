package pmd.ubi.pt.objects;

import java.util.Date;

import pmd.ubi.pt.LocalDatabase.DateTimeConverter;

/**
 * Created by Piotr on 29-Dec-16.
 */

public class Game
{
    private long id;
    private int numbersOfPlayers;
    private String currentText;
    private String date;
    private int wordMaximum;

    public Game()
    {

    }

    public Game(long _id, int _numberOfPlayers, String _currentText, Date _date, int _wordMaximum)
    {
        this.id = _id;
        this.numbersOfPlayers = _numberOfPlayers;
        this.currentText = _currentText;
        this.date = DateTimeConverter.dateToString(_date);
        this.wordMaximum = _wordMaximum;
    }

    //Getters
    public long getId()
    {
        return this.id;
    }
    public int getNumbersOfPlayers()
    {
        return this.numbersOfPlayers;
    }
    public String getCurrentText()
    {
        return this.currentText;
    }
    public String getDate()
    {
        return this.date;
    }
    public int getWordMaximum()
    {
        return this.wordMaximum;
    }

    //Setters
    public void setId(long _id)
    {
        this.id = _id;
    }
    public void setNumbersOfPlayers(int _numberOfPlayers)
    {
        this.numbersOfPlayers = _numberOfPlayers;
    }
    public void setCurrentText(String _currentText)
    {
        this.currentText = _currentText;
    }
    public void setDate(Date _date)
    {
        this.date = DateTimeConverter.dateToString(_date);
    }
    public void setWordMaximum(int _wordMaximum)
    {
        this.wordMaximum = _wordMaximum;
    }

    public String toString()
    {
        return "Id: " + id + " Number of Players: " + numbersOfPlayers + "Current text: " + currentText;
    }
}
