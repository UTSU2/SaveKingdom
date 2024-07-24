import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class TextSource {
	private Vector<String> v = new Vector<String>();
	public TextSource() { //파일에서 읽기
		try {
			File textfile = new File("WordNote.txt"); //텍스트파일 읽어오기
			FileReader fr = new FileReader(textfile); //파일 읽어오기 위해 선언
			StringBuffer sb = new StringBuffer();
			int c;
			while((c=fr.read()) != -1) { //파일의 끝에 가기 전까지 반복
				if((char)c == '\r') { //직접 입력하거나 프로그램 내에서 입력할 때
					v.add(sb.toString()); //벡터에 입력
					c=fr.read(); // \n 지우기
					sb.setLength(0); //버퍼 길이 초기화
				}
				else {
					sb.append((char)c); //버퍼에 입력
				}	
			}
			fr.close(); //모든 값 입력 시 파일 리더 닫기
		} catch (IOException e) {
			System.out.println("파일 복사 오류 발생");
			System.exit(0);
		}
		
	}
	
	public void InputText(String text) {	
		try {
			int i;
			File textfile = new File("WordNote.txt");
			String overlap = null;
			for(i=0;i<v.size();i++) { //벡터에서 모든 텍스트와 새로 입력된 텍스트를 비교
				overlap = v.get(i);
				if(overlap.equals(text)) //같은 단어가 있으면 반복 종료
					break;
			}
			//overlap = null;
			if(i == v.size()) { //같은 단어가 없었으면 텍스트 파일에 추가
				FileWriter fw = new FileWriter(textfile, true);
				fw.write(text+"\r\n"); //텍스트파일에 직접입력할 때와 같이 하기 위해서 \r\n(엔터키 입력시) 추가
				fw.close();
			}
		} catch (IOException e) {
			System.out.println("파일 복사 오류 발생");
			System.exit(0);
		}
	}
	
	public String get() { //랜덤한 값을 단어 벡터에서 가져와 반환
		int index = (int)(Math.random()*v.size());
		return v.get(index);
	}
}
