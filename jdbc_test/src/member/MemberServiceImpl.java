package member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class MemberServiceImpl implements MemberService{

	MemberDAO mDAO = new MemberDAO();
	Scanner sc = new Scanner(System.in);
	
	@Override
	public void startProgram() {
		
		int count = 0;
		
		while(true) {

			int choice = printMenu();

			switch(choice) {
			case 1 :
				displayMsg("1. 회원 정보 등록");
				insertMember();
				break;

			case 2 :
				displayMsg("2. 회원 정보 수정");
				updateMember();
				break;
				
			case 3 :
				displayMsg("3. 회원 정보 삭제");
				deleteMember();
				break;
				
			case 4 :
				displayMsg("4. 회원 정보 출력(이름)");
				printMember();
				break;

			case 5 :
				displayMsg("5. 회원 전체 정보 출력");
				printAllMembers();
				break;

			case 6 :
				displayMsg("프로그램 종료!!");
				count++;
				break;
				
			default :
				//("잘못된 숫자가 입력됨. 1~6 사이의 숫자 입력 가능");
				break;
			}

			if (count == 1) {
				break;
			}
		}
	}
	
	
	public int printMenu() {
		displayMsg("===== 회원 관리 프로그램 =====");
		displayMsg("1. 회원 정보 등록");
		displayMsg("2. 회원 정보 수정");
		displayMsg("3. 회원 정보 삭제");
		displayMsg("4. 회원 정보 출력(이름)");
		displayMsg("5. 회원 전체 정보 출력");
		displayMsg("6. 프로그램 종료");
		displayMsg("[선택] : ");

		int choice = sc.nextInt();

		return choice;
	}
	
	
	// 메시지 출력용
	public void displayMsg(String msg) {
		System.out.println(msg);
	}
	
	
	//1. 회원 정보 등록
	public void insertMember() {
		//회원 정보를 등록할 Member 객체 생성자
		Member member = new Member();

		//회원 정보 입력 받기
		System.out.print("회원 아이디 입력 : ");
		String memberId = sc.next();

		System.out.print("회원 비밀번호 : ");
		String memberPw = sc.next();

		System.out.print("회원 이름 : ");
		String memberName = sc.next();

		System.out.print("생년월일 : ");
		String memberBirth = sc.next();
		
		System.out.print("이메일 : ");
		String email = sc.next();

		System.out.print("연락처 : ");
		String phone = sc.next();

		//Member에 회원정보 셋팅
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setMemberBirth(memberBirth);
		member.setMemberEmail(email);
		member.setMemberPhone(phone);

		int chk = 0;
		chk = mDAO.insetMember(member);
		
		if(chk > 0) {
			System.out.println("등록되었습니다.");
			
		} else {
			System.out.println("등록에 실패하였습니다.");
		}
	}
	
	
	//2. 회원 정보 수정
	public void updateMember() {
		
		List<HashMap<String, Object>> memberList = new ArrayList();

		System.out.println("수정 할 회원명 입력하세요>>>>>>>>>>>>");
		sc.nextLine();
		String memberName = sc.nextLine();

		memberList = mDAO.printMember(memberName);
		
		System.out.println("회원ID\t회원이름\t생년월일\t이메일\t연락처");
		for(int i = 0; i < memberList.size(); i++) {
			System.out.print(memberList.get(i).get("memberId") + "\t");
			System.out.print(memberList.get(i).get("memberName") + "\t");
			System.out.print(memberList.get(i).get("memberBirth")+ "\t");
			System.out.print(memberList.get(i).get("email")+ "\t");
			System.out.println(memberList.get(i).get("phone"));
		}
		
		System.out.println("수정 할 회원의 순번을 입력하세요>>>>>>>>>>>>");
		int num = sc.nextInt();
		int memberIdx = Integer.parseInt(memberList.get(num-1).get("memberIdx").toString());
					
		System.out.println("수정 할 회원명을 입력하세요>>>>>>>>>>>>>");
		sc.nextLine();
		String updateName = sc.nextLine();
		
		int resultChk = 0;
		resultChk = mDAO.updateMember(memberIdx, updateName);
		
		if(resultChk > 0 ) {
			System.out.println(">>>>>>>>>>>>회원 정보 수정완료!");
		} else {
			System.out.println(">>>>>>>>>>>>회원명 수정에 실패했습니다.");
		}
	}
	
	
		
	//3. 회원 정보 삭제
	public void deleteMember() {
		
		List<HashMap<String, Object>> memberList = new ArrayList();

		System.out.println("삭제 할 회원명 입력하세요>>>>>>>>>>>>");
		sc.nextLine();
		String memberName = sc.nextLine();

		memberList = mDAO.printMember(memberName);
		
		System.out.println("회원ID\t회원이름\t생년월일\t이메일\t연락처");
		for(int i = 0; i < memberList.size(); i++) {
			System.out.print(memberList.get(i).get("memberId") + "\t");
			System.out.print(memberList.get(i).get("memberName") + "\t");
			System.out.print(memberList.get(i).get("memberBirth")+ "\t");
			System.out.print(memberList.get(i).get("email")+ "\t");
			System.out.println(memberList.get(i).get("phone"));
		}
		
		System.out.println("삭제 할 회원의 순번을 입력하세요>>>>>>>>>>>>");
		int num = sc.nextInt();
		int memberIdx = Integer.parseInt(memberList.get(num-1).get("memberIdx").toString());
		
		int resultChk = 0;
		resultChk = mDAO.deleteMember(memberIdx);
		
		if(resultChk > 0 ) {
			System.out.println(">>>>>>>>>>>>회원 정보 삭제완료!");
		} else {
			System.out.println(">>>>>>>>>>>>회원 삭제에 실패했습니다.");
		}
	}
	
	
	//4. 회원 정보 출력
	public void printMember() {
		List<HashMap<String, Object>> memberList = new ArrayList();
		
		System.out.println("검색 할 회원명을 입력하세요.>>>>>>>>>>>>>>>>>>");
		sc.nextLine();
		
		String memberName = sc.nextLine();
		memberList = mDAO.printMember(memberName);
		
		System.out.println("회원ID\t회원이름\t생년월일\t이메일\t연락처");
		for(int i = 0; i < memberList.size(); i++) {
			System.out.print(memberList.get(i).get("memberId") + "\t");
			System.out.print(memberList.get(i).get("memberName") + "\t");
			System.out.print(memberList.get(i).get("memberBirth")+ "\t");
			System.out.print(memberList.get(i).get("email")+ "\t");
			System.out.println(memberList.get(i).get("phone"));
		}
	}
	
	
	//5. 회원 전체정보 출력
	public void printAllMembers() {
		List<HashMap<String, Object>> memberList = new ArrayList();
		memberList = mDAO.findAllMember();
		
		System.out.println("회원ID\t회원이름\t생년월일\t이메일\t연락처");
		for(int i = 0; i < memberList.size(); i++) {
			System.out.print(memberList.get(i).get("memberId") + "\t");
			System.out.print(memberList.get(i).get("memberName") + "\t");
			System.out.print(memberList.get(i).get("memberBirth")+ "\t");
			System.out.print(memberList.get(i).get("email")+ "\t");
			System.out.println(memberList.get(i).get("phone"));
		}
	}
}
