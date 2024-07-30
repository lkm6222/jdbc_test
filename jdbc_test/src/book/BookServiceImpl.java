package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BookServiceImpl implements BookService{
	
	
	BookDAO bookDAO = new BookDAO();
	Scanner sc = new Scanner(System.in);

	@Override
	public void startProgram() {
				
		while(true) {

			int choice = printMenu();

			switch(choice) {
			case 1 :
				System.out.println("1. 도서 정보 등록");
				insertBook();
				break;

			case 2 :
				System.out.println("2. 도서 정보 수정");
				updateBook();
				break;
				
			case 3 :
				System.out.println("3. 도서 정보 삭제");
				deleteBooks();
				break;
				
			case 4 :
				System.out.println("4. 도서 정보 출력(도서명)");
				printBook();
				break;

			case 5 :
				System.out.println("5. 도서 전체 정보 출력");
				printAllBooks();
				break;

			case 6 :
				System.out.println("프로그램 종료!!");
				break;
				
			default :
				//("잘못된 숫자가 입력됨. 1~6 사이의 숫자 입력 가능");
				break;
			}
		}
		
	}

	@Override
	public int printMenu() {
		System.out.println("===== 도서 관리 프로그램 =====");
		System.out.println("1. 도서 정보 등록");
		System.out.println("2. 도서 정보 수정");
		System.out.println("3. 도서 정보 삭제");
		System.out.println("4. 도서 정보 출력(도서명)");
		System.out.println("5. 도서 전체 정보 출력");
		System.out.println("6. 프로그램 종료");
		System.out.println("[선택] : ");

		int choice = sc.nextInt();

		return choice;
	}

	public void insertBook() {
		
		System.out.println("도서명을 입력하세요>>>>>>>>>>>>");
		sc.nextLine();
		String title = sc.nextLine();
		
		System.out.println("도서가격을 입력하세요>>>>>>>>>>>>");
		int price = sc.nextInt();
		
		System.out.println("저자를 입력하세요>>>>>>>>>>>>");
		sc.nextLine();
		String author = sc.nextLine();
		
		System.out.println("출판사를 입력하세요>>>>>>>>>>>>");
		String publisher = sc.nextLine();
		
		System.out.println("출판년도를 입력하세요>>>>>>>>>>>>");
		String pubYear = sc.nextLine();
		
		System.out.println("ISBN을 입력하세요>>>>>>>>>>>>");
		String isbn = sc.nextLine();
		
		System.out.println("총 페이지수를 입력하세요>>>>>>>>>>>>");
		int page = sc.nextInt();
		
		
		BookInfo bookInfo = new BookInfo();
		bookInfo.setTitle(title);
		bookInfo.setPrice(price);
		bookInfo.setAuthor(author);
		bookInfo.setPublisher(publisher);
		bookInfo.setPubYear(pubYear);
		bookInfo.setIsbn(isbn);
		bookInfo.setPage(page);
		
		int resultChk = 0;
		resultChk = bookDAO.insertBook(bookInfo);
		
		if(resultChk > 0 ) {
			System.out.println(">>>>>>>>>>>>도서가 등록되었습니다.");
		} else {
			System.out.println(">>>>>>>>>>>>도서 등록에 실패했습니다.");
		}
	}
	

	
	//2. 도서 정보 수정
	public void updateBook() {

		List<HashMap<String, Object>> bookList = new ArrayList();

		System.out.println("검색 할 도서명을 입력하세요>>>>>>>>>>>>");
		sc.nextLine();
		String title = sc.nextLine();

		bookList = bookDAO.printSearchBooks(title);
		
		System.out.println("도서ID\t도서명\t저자\t출판사\t등록일");
		for(int i = 0; i < bookList.size(); i++) {
			System.out.print(bookList.get(i).get("book_id") + "\t");
			System.out.print(bookList.get(i).get("book_title") + "\t");
			System.out.print(bookList.get(i).get("book_author") + "\t");
			System.out.print(bookList.get(i).get("book_publisher")+ "\t");
			System.out.println(bookList.get(i).get("create_date"));
		}
		
		System.out.println("수정 할 도서의 순번을 입력하세요>>>>>>>>>>>>");
		int num = sc.nextInt();
		int bookId = (int) bookList.get(num-1).get("book_id");
		
		System.out.println("수정 할 도서명을 입력하세요>>>>>>>>>>>>>");
		sc.nextLine();
		String updateTitle = sc.nextLine();
		
		int resultChk = 0;
		resultChk = bookDAO.updateBook(bookId, updateTitle);
		
		if(resultChk > 0 ) {
			System.out.println(">>>>>>>>>>>>도서가 수정되었습니다.");
		} else {
			System.out.println(">>>>>>>>>>>>도서 수정에 실패했습니다.");
		}
	}

	
	
	
	//3. 도서 정보 삭제(도서명 기준)
	public void deleteBooks() {

		System.out.println("검색 할 도서명을 입력하세요>>>>>>>>>>>>");
		sc.nextLine();
		String title = sc.nextLine();
		
		int resultChk = 0;
		resultChk = bookDAO.deleteBooks(title);
		
		if(resultChk > 0 ) {
			System.out.println(">>>>>>>>>>>>도서가 삭제되었습니다.");
		} else {
			System.out.println(">>>>>>>>>>>>도서 삭제에 실패했습니다.");
		}
	}
	
	
	
	//4. 도서 정보 출력(도서명으로 검색)
	public void printBook() {
		
		List<HashMap<String, Object>> bookList = new ArrayList();

		System.out.println("검색 할 도서명을 입력하세요>>>>>>>>>>>>");
		sc.nextLine();
		String title = sc.nextLine();

		//입력 받은 title을 DAO에 넘겨서 리스트값 받아오기
		bookList = bookDAO.printSearchBooks(title);
		
		System.out.println("도서ID\t도서명\t저자\t출판사\t등록일");
		for(int i = 0; i < bookList.size(); i++) {
			System.out.print(bookList.get(i).get("book_id") + "\t");
			System.out.print(bookList.get(i).get("book_title") + "\t");
			System.out.print(bookList.get(i).get("book_author") + "\t");
			System.out.print(bookList.get(i).get("book_publisher")+ "\t");
			System.out.println(bookList.get(i).get("create_date"));
		}
	}
	
	
	// 5. 도서 전체 정보 출력
	public void printAllBooks() {
		
		List<HashMap<String, Object>> bookList = new ArrayList();
		bookList = bookDAO.printAllBooks();
		
		System.out.println("도서ID\t도서명\t저자\t출판사\t등록일");
		for(int i = 0; i < bookList.size(); i++) {
			System.out.print(bookList.get(i).get("book_id") + "\t");
			System.out.print(bookList.get(i).get("book_title") + "\t");
			System.out.print(bookList.get(i).get("book_author") + "\t");
			System.out.print(bookList.get(i).get("book_publisher")+ "\t");
			System.out.println(bookList.get(i).get("create_date"));
		}
	}
}
