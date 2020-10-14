# ReactiveAndroid
RxJava를 이용한 반응형 Android 네이티브 어플리케이션 학습

<a href="./01.ReactiveAndroid"><strong>01.ReactiveAndroid</strong></a>
<br>
간단한 RecyclerView를 이용하여 기본 io.reactivex.Observable 사용 해보기


<a href="./02.RetrofitAndroid"><strong>02.RetrofitAndroid</strong></a>
<br>
01.ReactiveAndroid 까지의 프로젝트를 바탕으로 Retrofit을 이용하여 온라인 상의 데이터 접근 및 RxJava를 활용하여 도메인 데이터로 병합


<a href="./03.RoomAndroid"><strong>03.RoomAndroid</strong></a>
<br>
02.RetrofitAndroid 까지의 프로젝트를 바탕으로 Room 라이브러리를 추가하여, RxJava 형태로 다루는 프로젝트, Repository, DataSource, LocalDataSource, RemoteDataSource 추가


<a href="./04.ErrorHandleAndroid"><strong>04.ErrorHandleAndroid</strong></a>
<br>
RxJava의 여러 Error 핸들링 기능을 접목. RxJavaPlugins 플러그인을 통해 중앙 집중식 에러 로깅 로직 추가 


<a href="./05.LifeCycleManageAndroid"><strong>05.LifeCycleManageAndroid</strong></a>
<br>
Rxappcompatactivity, RxFragment 등 Observable.compose(bindToLifecycle())을 사용하여 RxJava의 Observable의 메모리 누수를 막기 위한 작업


<a href="./06.FunctionalProgrammingAndroid"><strong>06.FunctionalProgrammingAndroid</strong></a>
<br>
Observable의 map(), flatmap(), switchMap(), filter(), firstElement() 등 다양한 순수 함수를 이용하여 기존 로직 수정
