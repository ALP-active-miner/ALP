package edu.iastate.cs.egroum.aug;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Paths used to easily performing regression testing to check that the approach still works
 * 
 * @author first_author
 *
 */
public class ALPRegressionTestConstants {

	public static Map<String, List<String>> javaFilesForApi = new HashMap<>();
	public static Map<String, List<String>> javaClassPathForApi = new HashMap<>();

	static {
		List<String> pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestDurationFieldType.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestDays.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestDateTimeZone.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestDateTimeFieldType.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestDateTimeComparator.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestDateMidnight_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestDateTime_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestDuration_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestHours.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestInstant_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestInterval_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestLocalDateTime_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestLocalDate_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestLocalTime_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestMinutes.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestMonthDay_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestMonths.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestMutableDateTime_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestMutableInterval_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestMutablePeriod_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestPartial_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestPeriodType.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestPeriodType.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestPeriod_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestSeconds.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestTimeOfDay_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestWeeks.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestYearMonthDay_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestYearMonth_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestYears.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/field/TestMillisDurationField.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/field/TestPreciseDurationField.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/field/TestScaledDurationField.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/tz/TestCachedDateTimeZone.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/test/java/org/joda/time/TestDateMidnight_Basics.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/main/java/org/joda/time/tz/DateTimeZoneBuilder.java",
				
//				// correct uses
				"/Users/first_author/Downloads/github-code-search/java.io.ObjectOutputStream__writeObject__1[ByteArrayOutputStream]_true/21924/com/selfimpr/storagedemo/StorageUtil.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.io.ObjectOutputStream__writeObject__1[ByteArrayOutputStream]_true/10755/com/slfuture/carrie/world/logic/Result.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.io.ObjectOutputStream__writeObject__1[ByteArrayOutputStream]_true/1518/esutdal/javanotes/cache/util/DefaultSerializer.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.io.ObjectOutputStream__writeObject__1[ByteArrayOutputStream]_true/10156/org/votingsystem/util/ObjectUtils.java.txt",
////				
//				// misuses
				"/Users/first_author/Downloads/github-code-search/java.io.ObjectOutputStream__writeObject__1[ByteArrayOutputStream]_true/1839/com/flowingsun/common/utils/SerializeUtils.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.io.ObjectOutputStream__writeObject__1[ByteArrayOutputStream]_true/100/org/sumanta/util/Serializer.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.io.ObjectOutputStream__writeObject__1[ByteArrayOutputStream]_true/1004/com/jdon/util/ObjectStreamUtil.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.io.ObjectOutputStream__writeObject__1[ByteArrayOutputStream]_true/10117/com/huateng/ebank/business/print/servlet/PrintServlet.java.txt"
//				
				);
		List<String> pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies", 
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				
				null, null, null, null, null, null, 
				null, null
				);
		javaFilesForApi.put("java.io.ObjectOutputStream__writeObject__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.io.ObjectOutputStream__writeObject__1", pathsToClassPath);

		// for java.lang.Long__parseLong__1
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/checkout/src/main/java/weiboclient4j/params/Cid.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/checkout/src/main/java/weiboclient4j/params/Id.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/checkout/src/main/java/weiboclient4j/params/Uid.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/checkout/src/main/java/weiboclient4j/params/Suid.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/checkout/src/main/java/weiboclient4j/params/TargetUid.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/checkout/src/main/java/weiboclient4j/params/SourceUid.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/checkout/src/main/java/weiboclient4j/params/TagId.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jriecken-gae-java-mini-profiler/80f3a59/checkout/src/main/java/com/google/appengine/tools/appstats/MiniProfilerAppstats.java",

				"/Users/first_author/repos/MUBench/mubench-checkouts/ivantrendafilov-confucius/2c30287/checkout/src/main/java/org/trendafilov/confucius/core/AbstractConfiguration.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/asterisk-java/41461b4/checkout/src/main/java/org/asteriskjava/manager/event/RtcpReceivedEvent.java",
//
				"/Users/first_author/Downloads/github-code-search/java.lang.Long__parseLong__1_true/502/org/stempeluhr/util/Parser.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.lang.Long__parseLong__1_true/470/com/bin/brother/integerLong/Test1.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.lang.Long__parseLong__1_true/1841/org/motechproject/nms/reportfix/kilkari/helpers/Parser.java.txt"

		);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/hoverruan-weiboclient4j/6ca0c73/dependencies",
				
				null,
				null, null,
				null,
				null,
				null);

		javaFilesForApi.put("java.lang.Long__parseLong__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.lang.Long__parseLong__1", pathsToClassPath);

		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/SimpleDefinitionFinder.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jackrabbit/1678/checkout/jackrabbit-jcr-server/src/main/java/org/apache/jackrabbit/webdav/jcr/JcrDavException.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/881//checkout/source/org/jfree/chart/plot/CategoryPlot.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/754/checkout//src/java/org/apache/lucene/search/FieldCacheImpl.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_directives/jsl/checkout/src/mubench/examples/directives/Unsynchronized.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/checkout/src/mubench/examples/survey/Maps.java"
//				"/Users/first_author/Downloads/github-code-search/java.util.Map__get__1_true/14640/javaapplication193/SequenceReconstruction.java.txt",
//				"/Users/first_author/Downloads/github-code-search/java.util.Map__get__1_true/8368/com/puzzle/SortMapByValue.java.txt",
//				"/Users/first_author/Downloads/github-code-search/java.util.Map__get__1_true/12190/myWorld/utils/Zpl_test.java.txt",
//				"/Users/first_author/Downloads/github-code-search/java.util.Map__get__1_true/17064/sds/chemicalexport/workers/CsvExport.java.txt",
//				"/Users/first_author/Downloads/github-code-search/java.util.Map__get__1_true/11906/com/yfy/crr/Analyser.java.txt",
//				"/Users/first_author/Downloads/github-code-search/java.util.Map__get__1_true/3362/edu/usc/ini/igc/ENIGMA/ml/MDD/J_SiteDictionary.java.txt"

		);
		pathsToClassPath = Arrays.asList("/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jackrabbit/1678/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/881/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/754/dependencies", 
				
				null, null
				);

		javaFilesForApi.put("java.util.Map__get__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.util.Map__get__1", pathsToClassPath);

		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/checkout/src/main/java/org/joda/time/tz/ZoneInfoCompiler.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/pdf/SimpleBookmark.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/pdf/CJKFont.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/html/WebColors.java",

				"/Users/first_author/Downloads/github-code-search/java.util.StringTokenizer__nextToken__0_true/939/com/practice/string/Op5_StringTokenizerTest.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.util.StringTokenizer__nextToken__0_true/1068/Hunter/H_92.java.txt",
				"/Users/first_author/Downloads/github-code-search/java.util.StringTokenizer__nextToken__0_true/1209/TokenPrueba2/TokenPrueba2.java.txt");
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jodatime/cc35fb2/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies", null, null, null

		);

		javaFilesForApi.put("java.util.StringTokenizer__nextToken__0", pathsToJavaFiles);
		javaClassPathForApi.put("java.util.StringTokenizer__nextToken__0", pathsToClassPath);

		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/corona-old/0d0d18b/checkout/src/com/corona/crypto/DESCypher.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/corona-old/0d0d18b/checkout/src/com/corona/crypto/AESCypher.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/alibaba-druid/e10f28/checkout/src/main/java/com/alibaba/druid/filter/config/ConfigTools.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/checkout/CS5430/src/server/generateChecksumPostsAndReplies.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/pdf/PdfPublicKeySecurityHandler.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jmrtd/67/checkout/src/sos/mrtd/PassportAuthService.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/minecraft-launcher/e62d1bb/checkout/src/main/java/net/minecraft/launcher/authentication/BaseAuthenticationService.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/saavn/e576758/checkout/src/saavn/cz/vity/freerapid/plugins/services/saavn/SaavnFileRunner.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/secure-tcp/checkout/src/main/java/org/network/stcp/server/SecureConnectionHandler.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_jca/jsl/checkout/src/mubench/examples/jca/Encrypting.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_jca/jsl/checkout/src/mubench/examples/jca/ReinitializingCipher.java");
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/corona-old/0d0d18b/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/corona-old/0d0d18b/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/alibaba-druid/e10f28/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/jmrtd/67/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/minecraft-launcher/e62d1bb/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/saavn/e576758/dependencies", null, null, null
		);

		javaFilesForApi.put("javax.crypto.Cipher__init__2", pathsToJavaFiles);
		javaClassPathForApi.put("javax.crypto.Cipher__init__2", pathsToClassPath);

		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jmrtd/51/checkout/src/sos/mrtd/SecureMessagingWrapper.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/mqtt/f438425/checkout/org.eclipse.paho.client.mqttv3/src/main/java/org/eclipse/paho/client/mqttv3/internal/wire/MqttSubscribe.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/mqtt/f438425/checkout/org.eclipse.paho.client.mqttv3/src/main/java/org/eclipse/paho/client/mqttv3/internal/wire/MqttUnsubscribe.java",
				
				"/Users/first_author/repos/MUBench/mubench-checkouts/bcel/24014e5/checkout/src/main/java/org/apache/commons/bcel6/generic/InstructionList.java",
				
				"/Users/first_author/repos/MUBench/mubench-checkouts/logblock-logblock-2/40548aa/checkout/LogBlock-2-API/src/test/java/org/logblock/entry/BlobTest.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/apache-gora/checkout/gora-accumulo/src/test/java/org/apache/gora/accumulo/store/PartitionTest.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/apache-gora/checkout/gora-core/src/test/java/org/apache/gora/util/TestWritableUtils.java",
				
				"/Users/first_author/repos/MUBench/mubench-checkouts/tbuktu-ntru/8126929/checkout/src/main/java/net/sf/ntru/encrypt/EncryptionParameters.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/tbuktu-ntru/8126929/checkout/src/main/java/net/sf/ntru/sign/SignatureParameters.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/tbuktu-ntru/8126929/checkout/src/main/java/net/sf/ntru/sign/SignaturePrivateKey.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/tbuktu-ntru/8126929/checkout/src/main/java/net/sf/ntru/sign/SignaturePublicKey.java"
				);
		
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jmrtd/51/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/mqtt/f438425/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/mqtt/f438425/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/bcel/24014e5/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/logblock-logblock-2/40548aa/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/apache-gora/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/apache-gora/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/tbuktu-ntru/8126929/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/tbuktu-ntru/8126929/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/tbuktu-ntru/8126929/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/tbuktu-ntru/8126929/dependencies"
		);
		javaFilesForApi.put("java.io.DataOutputStream__<init>__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.io.DataOutputStream__<init>__1", pathsToClassPath);

		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/testng/677302c/checkout/src/main/java/org/testng/xml/SuiteGenerator.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/checkout/src/mubench/examples/survey/SetFirst.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/checkout/src/mubench/examples/survey/OnlyNext.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/checkout/src/mubench/examples/survey/CME.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/checkout/src/java/org/apache/lucene/util/AttributeSource.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/checkout/src/java/org/apache/lucene/search/BooleanQuery.java", // has 2
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/pdf/PdfPKCS7.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/pdf/PdfWriter.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/awt/PdfGraphics2D.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/TypeCheck.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/JSModuleGraph.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/ReplaceMessages.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/TightenTypes.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/jsonml/Writer.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/deps/SortedDependencies.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/SimpleDefinitionFinder.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/argouml/026/checkout/src/argouml-app/src/org/argouml/persistence/ZargoFilePersister.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/argouml/026/checkout/src/argouml-app/src/org/argouml/uml/util/PathComparator.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/testng/677302c/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/argouml//026/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/argouml/026/dependencies"
);
		
		javaFilesForApi.put("java.util.Iterator__next__0", pathsToJavaFiles);
		javaClassPathForApi.put("java.util.Iterator__next__0", pathsToClassPath);
		
		// java.util.List__get__1
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/checkout/src/mubench/examples/survey/ListGet.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/deps/SortedDependencies.java",
				
				"/Users/first_author/repos/MUBench/mubench-checkouts/asterisk-java/304421c/checkout/src/main/java/org/asteriskjava/manager/internal/EventBuilderImpl.java",
//				"org/asteriskjava/manager/internal/EventBuilderImpl.java"
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_directives/jsl/checkout/src/mubench/examples/directives/TooRestrictive.java"
				
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/asterisk-java/304421c/dependencies",
				null
				
				);
		
		javaFilesForApi.put("java.util.List__get__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.util.List__get__1", pathsToClassPath);
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/checkout/CS5430/src/database/SocialNetworkDatabasePosts.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/checkout//CS5430/src/database/SocialNetworkDatabaseBoards.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/checkout/CS5430/src/database/DatabaseAdmin.java"
//				
//				"/Users/first_author/Downloads/github-code-search/java.sql.PreparedStatement__execute__0_true/2439/util/CriarBD.java.txt",
//				"/Users/first_author/Downloads/github-code-search/java.sql.PreparedStatement__executeUpdate__0_true/400/admin/dao/AdminDao.java.txt",
//				"/Users/first_author/Downloads/github-code-search/java.sql.PreparedStatement__executeQuery__0_true/2409/DAO/DaoBill.java.txt",
//				"/Users/first_author/Downloads/github-code-search/java.sql.PreparedStatement__executeUpdate__0_true/2001/com/crudservletfreemaker/dao/CrudDao.java.txt" // wrong
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/dependencies"
//				
//				null,
//				null,
//				null,
//				null
				);
		
		javaFilesForApi.put("java.sql.PreparedStatement__execute*__0", pathsToJavaFiles);
		javaClassPathForApi.put("java.sql.PreparedStatement__execute*__0", pathsToClassPath);
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/checkout/src/java/org/apache/lucene/index/MergeDocIDRemapper.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/checkout/src/java/org/apache/lucene/index/IndexWriter.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/dependencies"
				);
		
		javaFilesForApi.put("org.apache.lucene.index.SegmentInfos__info__1", pathsToJavaFiles);
		javaClassPathForApi.put("org.apache.lucene.index.SegmentInfos__info__1", pathsToClassPath);
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/164/checkout/source/org/jfree/chart/renderer/category/StatisticalBarRenderer.java"
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/164/dependencies"
				);
		
		javaFilesForApi.put("org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2", pathsToJavaFiles);
		javaClassPathForApi.put("org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2", pathsToClassPath);
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/164/checkout/source/org/jfree/chart/renderer/category/StatisticalBarRenderer.java"
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/164/dependencies"
				);
		
		javaFilesForApi.put("org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2", pathsToJavaFiles);
		javaClassPathForApi.put("org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2", pathsToClassPath);
		
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author//repos/MUBench/mubench-checkouts/thomas-s-b-visualee/410a80f/checkout/src/main/java/de/strullerbaumann/visualee/examiner/Examiner.java"
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author//repos/MUBench/mubench-checkouts/thomas-s-b-visualee/410a80f/dependencies"
				);
		
		
		javaFilesForApi.put("java.util.Scanner__next__0", pathsToJavaFiles);
		javaClassPathForApi.put("java.util.Scanner__next__0", pathsToClassPath);
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/pdf/SimpleBookmark.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/pdf/PdfStructureTreeRoot.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/pdf/fonts/cmaps/AbstractCMap.java"
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies"
				);
		
		
		javaFilesForApi.put("com.itextpdf.text.pdf.PdfArray__getPdfObject__1", pathsToJavaFiles);
		javaClassPathForApi.put("com.itextpdf.text.pdf.PdfArray__getPdfObject__1", pathsToClassPath);
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/checkout/CS5430/src/database/SocialNetworkDatabasePosts.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/checkout/CS5430/src/database/SocialNetworkDatabaseBoards.java"
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/dependencies"
				);
		javaFilesForApi.put("java.sql.ResultSet__next__0", pathsToJavaFiles);
		javaClassPathForApi.put("java.sql.ResultSet__next__0", pathsToClassPath);
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/ivantrendafilov-confucius/2c30287/checkout/src/main/java/org/trendafilov/confucius/core/AbstractConfiguration.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/ivantrendafilov-confucius/2c30287/dependencies"
				
				);
		javaFilesForApi.put("java.lang.Byte__parseByte__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.lang.Byte__parseByte__1", pathsToClassPath);
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jigsaw/205/checkout/Jigsaw/src/classes/org/w3c/jigsaw/servlet/JigsawHttpServletRequest.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jigsaw/205/dependencies"
				
				);
		javaFilesForApi.put("java.util.Enumeration__nextElement__0", pathsToJavaFiles);
		javaClassPathForApi.put("java.util.Enumeration__nextElement__0", pathsToClassPath);
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/2183/checkout/source/org/jfree/chart/plot/XYPlot.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/2183/dependencies"
				);
		
		javaFilesForApi.put("org.jfree.chart.plot.XYPlot__getRendererForDataset__1", pathsToJavaFiles);
		javaClassPathForApi.put("org.jfree.chart.plot.XYPlot__getRendererForDataset__1", pathsToClassPath);
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/103/checkout/source/org/jfree/chart/axis/Axis.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/103/dependencies"
				);
		
		javaFilesForApi.put("org.jfree.chart.plot.PlotRenderingInfo__getOwner__0", pathsToJavaFiles);
		javaClassPathForApi.put("org.jfree.chart.plot.PlotRenderingInfo__getOwner__0", pathsToClassPath);
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/checkout/itext/src/main/java/com/itextpdf/text/pdf/PdfLayer.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/dependencies"
				);
		
		javaFilesForApi.put("com.itextpdf.text.pdf.PdfDictionary__getAsString__1", pathsToJavaFiles);
		javaClassPathForApi.put("com.itextpdf.text.pdf.PdfDictionary__getAsString__1", pathsToClassPath);
		
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/ivantrendafilov-confucius/2c30287/checkout/src/main/java/org/trendafilov/confucius/core/AbstractConfiguration.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/ivantrendafilov-confucius/2c30287/dependencies"
				);
		
		javaFilesForApi.put("java.lang.Short__parseShort__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.lang.Short__parseShort__1", pathsToClassPath);
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/2266/checkout/source/org/jfree/chart/renderer/category/AbstractCategoryItemRenderer.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/jfreechart/2266/dependencies"
				);
		
		javaFilesForApi.put("org.jfree.chart.plot.CategoryPlot__getDataset__1", pathsToJavaFiles);
		javaClassPathForApi.put("org.jfree.chart.plot.CategoryPlot__getDataset__1", pathsToClassPath);
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/checkout/src/mubench/examples/survey/ByteBufferFlip.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/dependencies"
				);
		
		javaFilesForApi.put("java.nio.ByteBuffer__put__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.nio.ByteBuffer__put__1", pathsToClassPath);
		
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/checkout/src/java/org/apache/lucene/index/ParallelReader.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918//dependencies"
				);
		
		javaFilesForApi.put("java.util.SortedMap__firstKey__0", pathsToJavaFiles);
		javaClassPathForApi.put("java.util.SortedMap__firstKey__0", pathsToClassPath);
		
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/CommandLineRunner.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies"
				);
		
		javaFilesForApi.put("org.kohsuke.args4j.spi.Parameters__getParameter__1", pathsToJavaFiles);
		javaClassPathForApi.put("org.kohsuke.args4j.spi.Parameters__getParameter__1", pathsToClassPath);
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/checkout/src/mubench/examples/survey/ChannelFlush.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/dependencies"
				);
		
		javaFilesForApi.put("java.nio.channels.FileChannel__write__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.nio.channels.FileChannel__write__1", pathsToClassPath);
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/checkout/src/mubench/examples/survey/Close.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/dependencies"
				);
		
		javaFilesForApi.put("java.io.PrintWriter__write__1", pathsToJavaFiles);
		javaClassPathForApi.put("java.io.PrintWriter__write__1", pathsToClassPath);
		
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/checkout/src/mubench/examples/survey/JFramePack.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_directives/jsl/checkout/src/mubench/examples/directives/CallOnDTE.java",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_directives/jsl/checkout/src/mubench/examples/directives/AlreadyOnDTE.java"
				
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_survey/jsl/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_directives/jsl/dependencies",
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_directives/jsl/dependencies"
				);
		
		javaFilesForApi.put("javax.swing.JFrame__setVisible__1", pathsToJavaFiles);
		javaClassPathForApi.put("javax.swing.JFrame__setVisible__1", pathsToClassPath);
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_java8-misuses/96d0ccb/checkout/src/com/xpinjection/java8/misused/optional/HundredAndOneApproach.java"
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/synthetic_java8-misuses/96d0ccb//dependencies"
				);
		
		javaFilesForApi.put("java.util.Optional__get__0", pathsToJavaFiles);
		javaClassPathForApi.put("java.util.Optional__get__0", pathsToClassPath);
		
		
		
		
		pathsToJavaFiles = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/checkout/src/com/google/javascript/jscomp/SimpleDefinitionFinder.java"
				);
		pathsToClassPath = Arrays.asList(
				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/dependencies"
				);
		
		javaFilesForApi.put("com.google.common.collect.Multimap__get__1", pathsToJavaFiles);
		javaClassPathForApi.put("com.google.common.collect.Multimap__get__1", pathsToClassPath);
		
	}
	
	

}
