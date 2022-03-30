package com.duck.jpa1.services;

import com.duck.jpa1.domain.Author;
import com.duck.jpa1.domain.Book;
import com.duck.jpa1.repositories.AuthorRepository;
import com.duck.jpa1.repositories.BookRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
public class TestService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public TestService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void test() {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findAll().forEach(books::add);

        // for (Book book : books) {
        //     printBook(book, "TestService");
        // }

        Book book = books.get(5);
        printBook(book, "TestService: 1");

        Author author = new Author("Qwack", "Mcduck");
        book.getAuthors().add(author);
        author.getBooks().add(book);

        authorRepository.save(author);
        bookRepository.save(book);

        printBook(book, "TestService: 2");
    }

    @Transactional
    public void testLoad() {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findByTitle("DARKNESS AT NOON").forEach(books::add);
        Book book = books.get(0);

        System.out.println("---------------------------------------------------- testLoad: 1");
        //System.out.println(String.format("*********************************************** Book(%s) Id(%d)", book.getTitle(), book.getId()));
        printBook(book, "testLoad: 1");
        System.out.println("---------------------------------------------------- testLoad: 2");

    }

    @Transactional
    public void doSetup() throws Exception {
        Author[] authors = {new Author("Eric", "Evans")
                            ,new Author("John", "Fort")
                            ,new Author("Jane", "MacNee")
                            ,new Author("Zaphod", "Beeblebrox")
                            ,new Author("Justin", "Notherguy")
                            ,new Author("Hu", "Dini")
                            ,new Author("Wally", "Russ")
                            ,new Author("Biggs", "Boggs")
                            ,new Author("Helene", "DeTroy")
        };

        Book[] books = { new Book("ULYSSES", UUID.randomUUID().toString())
                        ,new Book("THE GREAT GATSBY", UUID.randomUUID().toString())
                        ,new Book("A PORTRAIT OF THE ARTIST AS A YOUNG MAN", UUID.randomUUID().toString())
                        ,new Book("LOLITA", UUID.randomUUID().toString())
                        ,new Book("BRAVE NEW WORLD", UUID.randomUUID().toString())
                        ,new Book("THE SOUND AND THE FURY", UUID.randomUUID().toString())
                        ,new Book("CATCH-22", UUID.randomUUID().toString())
                        ,new Book("DARKNESS AT NOON", UUID.randomUUID().toString())
                        ,new Book("SONS AND LOVERS", UUID.randomUUID().toString())
                        ,new Book("THE GRAPES OF WRATH", UUID.randomUUID().toString())
                        ,new Book("UNDER THE VOLCANO", UUID.randomUUID().toString())
                        ,new Book("THE WAY OF ALL FLESH", UUID.randomUUID().toString())
                        ,new Book("1984", UUID.randomUUID().toString())
                        ,new Book("I, CLAUDIUS", UUID.randomUUID().toString())
                        ,new Book("TO THE LIGHTHOUSE", UUID.randomUUID().toString())
                        ,new Book("AN AMERICAN TRAGEDY", UUID.randomUUID().toString())
                        ,new Book("THE HEART IS A LONELY HUNTER", UUID.randomUUID().toString())
                        ,new Book("SLAUGHTERHOUSE-FIVE", UUID.randomUUID().toString())
                        ,new Book("INVISIBLE MAN", UUID.randomUUID().toString())
                        ,new Book("NATIVE SON", UUID.randomUUID().toString())
                        ,new Book("HENDERSON THE RAIN KING", UUID.randomUUID().toString())
        };


        for (Author author : authors) {
            authorRepository.save(author);
        }

        for (Book book : books) {
            bookRepository.save(book);
        }

        for (Author author : authors) {
            System.out.println(String.format("*********************************************** Author: %s author(%d)", author.getFirstName(), author.getId()));
            //authorRepository.save(author);
            int numBooks = randomNum(3, 6);
            List<Integer> randomBookIdxs = uniqueRandomNums(numBooks, 0, books.length-1);
            for (int bookidx : randomBookIdxs) {
                author.getBooks().add(books[bookidx]);
                books[bookidx].getAuthors().add(author);
                System.out.println(String.format("*************************************************** Add Book: %s author(%d) book(%d)", 
                    books[bookidx].getTitle(), author.getId(), books[bookidx].getId()));
            }

        }

        System.out.println("################################# S A V I N G   A U T H O R S ########################################");
        for (Author author : authors) {
            System.out.println(String.format("*********************************************** Author(%s) Id(%d)", author.getFirstName(), author.getId()));
            for (Book book : author.getBooks()) {
                System.out.println(String.format("******************************************************* Book(%s) Id(%d)", book.getTitle(), book.getId()));
            }
            authorRepository.save(author);
        }

        System.out.println("################################# S A V I N G   B O O K S     ########################################");
        for (Book book : books) {
            System.out.println(String.format("*********************************************** Book(%s) Id(%d)", book.getTitle(), book.getId()));
            for (Author author : book.getAuthors()) {
                System.out.println(String.format("******************************************************* Author(%s) Id(%d)", author.getFirstName(), author.getId()));
            }
            bookRepository.save(book);
        }

        System.out.println("#################################   A D D   N E W   A U T H O R     ########################################");
        Author author = new Author("Erica", "Evanson");
        Book book = books[8];
        authorRepository.save(author);
        //book.getAuthors().clear();
        book.getAuthors().add(author);
        bookRepository.save(book);

        // select * from author a
        // inner join author_book ab on ab.author_id =a.id
        // inner join book b on b.id = ab.book_id

        System.out.println("#################################    L I S T I N G S     ########################################");
        
        book = books[8];
        printBook(book, "book");

        // book.getAuthors().clear();
        // printBook(book, "book clear");

        // bookRepository.findById(book.getId());
        // printBook(book, "book load");
        
        //Set<Author> authorsSet = authorRepository.findAllByBooksContains(book);


        //

        System.out.println("#################################    L I S T I N G S   2    ########################################");



    }

    private List<Integer> uniqueRandomNums(int n, int min, int max) throws Exception {

        if ((max - min + 1) <= 2) {
            throw new Exception("uniqueRandomNums: max-min+1 must be 2 or more. i.e. gen at least 2 random nums.");
        }

        if ((max - min + 1) < n) {
            throw new Exception("uniqueRandomNums: max-min+1 must be >= 'n' the number of unique random nums to generate.");
        }

        List<Integer> randomNums = new ArrayList<Integer>();

        for (int i=0; i<n; i++) {
            int maxloops = n * 100;
            //System.out.println("***************************************************************");
            while(true) {
                maxloops--;
                if (maxloops <= 0) {
                    throw new Exception("uniqueRandomNums: max loops exceeded.");
                }
                int rnum = randomNum(min, max);
                //System.out.println("rnum: " + rnum);
                if (randomNums.contains(rnum)) continue;
                randomNums.add(rnum);
                break;
            }
        }

        return randomNums;
    }

    private int randomNum(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }


    private void printBook(Book book, String header) {
        System.out.println(String.format("************************ %s *****************************", header));
        System.out.println(String.format("************************ %s *****************************", header));
        System.out.println(String.format("************************ %s *****************************", header));
        System.out.println(String.format("*********************************************** Book(%s) Id(%d)", book.getTitle(), book.getId()));
        //for (Author author2 : authorsSet) {
        for (Author author : book.getAuthors()) {
            System.out.println(String.format("******************************************************* Author(%s) Id(%d)", author.getFirstName(), author.getId()));
        }
    }
}
