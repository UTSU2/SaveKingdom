import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class GradeSource {
	private Vector<String> nameV = new Vector<String>(); //이름 벡터
	private Vector<String> gradeV = new Vector<String>(); //점수 벡터
	private Vector<String> tmpnameV = new Vector<String>(); //임시 이름 벡터
	private Vector<String> tmpgradeV = new Vector<String>(); //임시 점수 벡터
	public GradeSource() {
		try {
			File textfile = new File("Grade.txt"); //텍스트파일 읽어오기
			FileReader fr = new FileReader(textfile); //파일 읽어오기 위해 선언
			StringBuffer sb = new StringBuffer();
			int c;
			while((c=fr.read()) != -1) {
				if((char)c == '\r') { //직접 텍스트파일에 입력할 시
					String GradeAndName = sb.toString(); //이름 점수 형태의 문자열 저장
					String [] array = GradeAndName.split(" "); //" " 기준으로 나누기
					nameV.add(array[0]); //앞부분 이름 벡터에 저장
					gradeV.add(array[1]); //뒷부분 점수 벡터에 저장
					c=fr.read(); // \n부분 읽어 지우기
					sb.setLength(0); //버퍼 길이 0으로 초기화
				}
				else {
					sb.append((char)c); // \r 나오기 전까지 버퍼에 읽어들이기
				}
			}
			fr.close(); //다 읽으면 파일 리더 닫기
			arrangeVector(); //벡터에 점수 순대로 정렬
		} catch (IOException e) {
			System.out.println("파일 복사 오류 발생");
			System.exit(0);
		}
	}
	public void InputGrade(String name, int grade) { //텍스트 파일에 이름과 점수 입력
		try {
			int i;
			File textfile = new File("Grade.txt");
			FileWriter fw = new FileWriter(textfile, true); //파일에 작성하기 위해 선언
			fw.write(name + " " + Integer.toString(grade) + "\r\n"); //텍스트파일에 직접입력할 때와 같이 하기 위해서 \r\n(엔터키 입력시) 추가
			fw.close(); //파일 라이터 닫기
		} catch (IOException e) {
			System.out.println("파일 복사 오류 발생");
			System.exit(0);
		}
		arrangeVector(); //벡터에 점수 순대로 정렬
	}
	private void arrangeVector() { //벡터 정렬
		tmpnameV.clear(); //임시 벡터 2개 초기화
		tmpgradeV.clear();
		String tmpName = null;
		int tmpGrade;
		boolean ifadd = false;
		for(int i=1;i<gradeV.size();i++) { //임시 벡터에 점수 순서대로 정렬
			ifadd = false;
			tmpName = nameV.get(i);
			tmpGrade = Integer.parseInt(gradeV.get(i));
			if(tmpgradeV.size() == 0) {
				tmpnameV.add(nameV.get(0));
				tmpgradeV.add(gradeV.get(0));
			}
			for(int j=0;j<tmpgradeV.size();j++) {
				if(Integer.parseInt(tmpgradeV.get(j)) < tmpGrade) { //기존에 임시 벡터에 있는 점수와 비교 후 입력
					tmpnameV.add(j,tmpName);
					tmpgradeV.add(j,Integer.toString(tmpGrade));
					ifadd = true;
					break;
				}
			}
			if(ifadd == false) { //입력된 모든 점수보다 낮은 점수일 시 마지막에 추가
				tmpnameV.add(tmpName);
				tmpgradeV.add(Integer.toString(tmpGrade));
			}
		}
		nameV.clear(); //기존 벡터 2개 모두 초기화
		gradeV.clear();
		for(int i=0;i<tmpgradeV.size();i++) { //임시 벡터의 내용을 기존 벡터에 복사
			nameV.add(tmpnameV.get(i));
			gradeV.add(tmpgradeV.get(i));
		}
	}
	public int getVectorSize() { //벡터 크기 가져오기 (이름 벡터의 크기와 점수 벡터의 크기는 같음)
		return gradeV.size();
	}
	public String getName(int i) { //이름 벡터 내용 전달
		return nameV.get(i);
	}
	public String getGrade(int i) { //점수 벡터 내용 전달
		return gradeV.get(i);
	}
}
