package edu.charlotte.projectapp6;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    static private ArrayList<Book> allBooks = new ArrayList<Book>(){
        {
            add(new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, "Fantasy"));
            add(new Book("Pride and Prejudice", "Jane Austen", 1813, "Romance"));
            add(new Book("To Kill a Mockingbird", "Harper Lee", 1960, "Southern Gothic"));
            add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "Classic"));
            add(new Book("One Hundred Years of Solitude", "Gabriel Garcia Marquez", 1967, "Magical Realism"));
            add(new Book("War and Peace", "Leo Tolstoy", 1869, "Historical Fiction"));
            add(new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866, "Psychological Thriller"));
            add(new Book("Hamlet", "William Shakespeare", 1603, "Play"));
            add(new Book("Moby Dick", "Herman Melville", 1851, "Adventure"));
            add(new Book("The Adventures of Huckleberry Finn", "Mark Twain", 1885, "Satire"));
            add(new Book("The Adventures of Tom Sawyer", "Mark Twain", 1876, "Adventure"));
            add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951, "Coming-of-age"));
            add(new Book("The Handmaid's Tale", "Margaret Atwood", 1985, "Dystopian"));
            add(new Book("The Book Thief", "Markus Zusak", 2005, "Historical Fiction"));
            add(new Book("The Kite Runner", "Khaled Hosseini", 2003, "Historical Fiction"));
            add(new Book("Little Women", "Louisa May Alcott", 1868, "Coming-of-age"));
            add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", 1997, "Fantasy"));
            add(new Book("The Hunger Games", "Suzanne Collins", 2008, "Dystopian"));
            add(new Book("Divergent", "Veronica Roth", 2011, "Dystopian"));
            add(new Book("A Song of Ice and Fire", "George R.R. Martin", 1996, "Fantasy"));
            add(new Book("The Martian", "Andy Weir", 2011, "Science Fiction"));
            add(new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", 1979, "Science Fiction"));
            add(new Book("Dune", "Frank Herbert", 1965, "Science Fiction"));
            add(new Book("Foundation", "Isaac Asimov", 1951, "Science Fiction"));
            add(new Book("Ender's Game", "Orson Scott Card", 1985, "Science Fiction"));
            add(new Book("The Time Machine", "H.G. Wells", 1895, "Science Fiction"));
            add(new Book("Neuromancer", "William Gibson", 1984, "Cyberpunk"));
            add(new Book("Snow Crash", "Neal Stephenson", 1992, "Cyberpunk"));
            add(new Book("The Left Hand of Darkness", "Ursula K. Le Guin", 1969, "Science Fiction"));
            add(new Book("The Dispossessed", "Ursula K. Le Guin", 1974, "Science Fiction"));
            add(new Book("The Three-Body Problem", "Cixin Liu", 2008, "Science Fiction"));
            add(new Book("The Andromeda Strain", "Michael Crichton", 1969, "Science Fiction"));
            add(new Book("Jurassic Park", "Michael Crichton", 1990, "Science Fiction"));
            add(new Book("The Da Vinci Code", "Dan Brown", 2003, "Mystery"));
            add(new Book("Angels & Demons", "Dan Brown", 2000, "Mystery"));
            add(new Book("Gone Girl", "Gillian Flynn", 2012, "Thriller"));
            add(new Book("The Girl with the Dragon Tattoo", "Stieg Larsson", 2005, "Crime"));
            add(new Book("The Silence of the Lambs", "Thomas Harris", 1988, "Thriller"));
            add(new Book("The Shining", "Stephen King", 1977, "Horror"));
            add(new Book("Misery", "Stephen King", 1987, "Psychological Horror"));
            add(new Book("It", "Stephen King", 1986, "Horror"));
            add(new Book("The Stand", "Stephen King", 1978, "Horror"));
            add(new Book("Pet Sematary", "Stephen King", 1983, "Horror"));
            add(new Book("Salem's Lot", "Stephen King", 1975, "Horror"));
            add(new Book("The Green Mile", "Stephen King", 1996, "Magical Realism"));
            add(new Book("The Dark Tower", "Stephen King", 1982, "Dark Fantasy"));
            add(new Book("The Drawing of the Three", "Stephen King", 1987, "Dark Fantasy"));
            add(new Book("The Waste Lands", "Stephen King", 1991, "Dark Fantasy"));
            add(new Book("Wizard and Glass", "Stephen King", 1997, "Dark Fantasy"));
            add(new Book("The Lord of the Flies", "William Golding", 1954, "Allegory"));
            add(new Book("Jane Eyre", "Charlotte Brontë", 1847, "Gothic"));
            add(new Book("Frankenstein", "Mary Shelley", 1818, "Gothic"));
            add(new Book("Wuthering Heights", "Emily Brontë", 1847, "Gothic"));
            add(new Book("1984", "George Orwell", 1949, "Dystopian"));
            add(new Book("Brave New World", "Aldous Huxley", 1932, "Dystopian"));
            add(new Book("The Road", "Cormac McCarthy", 2006, "Post-Apocalyptic"));
            add(new Book("The Remains of the Day", "Kazuo Ishiguro", 1989, "Historical Fiction"));
            add(new Book("The Name of the Rose", "Umberto Eco", 1980, "Historical Mystery"));
            add(new Book("Beloved", "Toni Morrison", 1987, "Historical Fiction"));
            add(new Book("The Color Purple", "Alice Walker", 1982, "Epistolary"));
            add(new Book("The God of Small Things", "Arundhati Roy", 1997, "Family Saga"));
            add(new Book("Life of Pi", "Yann Martel", 2001, "Adventure"));
            add(new Book("The Curious Incident of the Dog in the Night-Time", "Mark Haddon", 2003, "Mystery"));
            add(new Book("The Secret History", "Donna Tartt", 1992, "Mystery"));
            add(new Book("The Goldfinch", "Donna Tartt", 2013, "Coming-of-age"));
            add(new Book("Middlesex", "Jeffrey Eugenides", 2002, "Historical Fiction"));
            add(new Book("The Poisonwood Bible", "Barbara Kingsolver", 1998, "Historical Fiction"));
            add(new Book("A Prayer for Owen Meany", "John Irving", 1989, "Coming-of-age"));
            add(new Book("The World According to Garp", "John Irving", 1978, "Fiction"));
            add(new Book("The Cider House Rules", "John Irving", 1985, "Fiction"));
            add(new Book("The Wind-Up Bird Chronicle", "Haruki Murakami", 1994, "Magical Realism"));
            add(new Book("Norwegian Wood", "Haruki Murakami", 1987, "Fiction"));
            add(new Book("Kafka on the Shore", "Haruki Murakami", 2002, "Magical Realism"));
            add(new Book("The Alchemist", "Paulo Coelho", 1988, "Philosophical Fiction"));
            add(new Book("The Pilgrimage", "Paulo Coelho", 1987, "Philosophical Fiction"));
            add(new Book("Veronika Decides to Die", "Paulo Coelho", 1998, "Philosophical Fiction"));
            add(new Book("The Devil and Miss Prym", "Paulo Coelho", 2000, "Philosophical Fiction"));
            add(new Book("Eleven Minutes", "Paulo Coelho", 2003, "Philosophical Fiction"));
            add(new Book("The Zahir", "Paulo Coelho", 2005, "Philosophical Fiction"));
            add(new Book("The Witch of Portobello", "Paulo Coelho", 2006, "Philosophical Fiction"));
            add(new Book("The Winner Stands Alone", "Paulo Coelho", 2008, "Philosophical Fiction"));
            add(new Book("Aleph", "Paulo Coelho", 2010, "Philosophical Fiction"));
            add(new Book("Manuscript Found in Accra", "Paulo Coelho", 2012, "Philosophical Fiction"));
            add(new Book("Adultery", "Paulo Coelho", 2014, "Philosophical Fiction"));
            add(new Book("The Spy", "Paulo Coelho", 2016, "Historical Fiction"));
            add(new Book("Hippie", "Paulo Coelho", 2018, "Autobiographical Fiction"));
            add(new Book("The Archer", "Paulo Coelho", 2020, "Philosophical Fiction"));
            add(new Book("Eleven Minutes", "Paulo Coelho", 2003, "Philosophical Fiction"));
            add(new Book("The Fifth Mountain", "Paulo Coelho", 1996, "Philosophical Fiction"));
            add(new Book("By the River Piedra I Sat Down and Wept", "Paulo Coelho", 1994, "Philosophical Fiction"));
            add(new Book("The Valkyries", "Paulo Coelho", 1992, "Philosophical Fiction"));
            add(new Book("Brida", "Paulo Coelho", 1990, "Philosophical Fiction"));
        }
    };
    static private HashMap<String, ArrayList<Book>> map;

    static public ArrayList<String> getAllGenres(){
        setupMap();
        return new ArrayList<>(map.keySet());
    }

    static public ArrayList<Book> getBooksByGenre(String genre){
        setupMap();
        return map.get(genre);
    }

    static private void setupMap(){
        if(map == null){
            map = new HashMap<>();
            for(Book book: allBooks){
                if(map.containsKey(book.getGenre())){
                    map.get(book.getGenre()).add(book);
                }else{
                    ArrayList<Book> list = new ArrayList<>();
                    list.add(book);
                    map.put(book.getGenre(), list);
                }
            }
        }
    }
}
