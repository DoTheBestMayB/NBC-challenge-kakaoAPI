## Note

### OkHttpLogging 시, BuildConfig 대신 release, debug folder를 이용

동일한 클래스에 다른 값을 리턴하는 방법으로 BuildConfig 없이 release, debug에 따라 다른 설정을 할 수 있다.

https://blog.dipien.com/stop-generating-the-buildconfig-on-your-android-modules-7d82dd7f20f1
https://archive.is/UhkME

### String 문자열을 LocalDateTime으로 변환하기

LocalDate : 날짜를 표기하는 데 사용하는 java.time 패키지 클래스
LocalTime : 시간을 표기하는 데 사용하는 java.time 패키지 클래스
LocalDateTime : LocalDate + LocalTime

EpochTime : 1970-01-01 00:00:00 UTC
Instant : EpochTime으로 부터 경과된 시간을 나노초 단위로 표현한 것

https://www.baeldung.com/java-date-to-localdate-and-localdatetime
https://stackoverflow.com/questions/63448838/convert-string-to-localdatetime-java-8

### Service에서 @Query 대신 @Body 에노테이션을 사용

@Body : Request Body에 요청 파라미터를 넣는다.
@Query : 요청 Url에 ?Key=Value 형태로 파라미터를 덧붙인다.
https://stackoverflow.com/a/47409159/11722881

### Dao 의존성 주입시 singleton 애노테이션을 붙이지 않음

Database 내부에서 Dao 객체를 싱글턴으로 반환하고 있기 때문에, Database만 singleton으로 주입하면 Dao도 자동으로 Singleton으로 사용할 수 있다.

![image](https://github.com/user-attachments/assets/3ac99e4a-5885-4a69-9567-39ed9cdd9c25)
