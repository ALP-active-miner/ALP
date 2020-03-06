#!/bin/bash

# in practice, the ALP system cannot deal with some API, so no need to run ALP for them. However, need to include them in the testing data so these APIs are commented out for easy access
# names='java.io.ObjectOutputStream__writeObject__1 java.lang.Long__parseLong__1 java.util.Map__get__1  java.util.List__get__1  java.util.StringTokenizer__nextToken__0 javax.crypto.Cipher__init__2 java.io.DataOutputStream__<init>__1 java.sql.PreparedStatement__execute*__0  java.util.Iterator__next__0 org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2 java.util.Scanner__next__0 com.itextpdf.text.pdf.PdfArray__getPdfObject__1 java.sql.ResultSet__next__0 org.apache.lucene.index.SegmentInfos__info__1 java.lang.Byte__parseByte__1 java.lang.Short__parseShort__1 java.util.Enumeration__nextElement__0 org.jfree.chart.plot.XYPlot__getRendererForDataset__1 org.jfree.chart.plot.PlotRenderingInfo__getOwner__0 org.jfree.chart.plot.CategoryPlot__getDataset__1 com.itextpdf.text.pdf.PdfDictionary__getAsString__1 java.nio.ByteBuffer__put__1 java.util.SortedMap__firstKey__0 org.kohsuke.args4j.spi.Parameters__getParameter__1 java.nio.channels.FileChannel__write__1  java.io.PrintWriter__write__1 javax.swing.JFrame__setVisible__1 java.util.Optional__get__0 org.apache.commons.lang.text.StrBuilder__getNullText__0 org.apache.commons.math3.geometry.euclidean.threed.Line__intersection__1 org.apache.commons.math3.geometry.euclidean.twod.Line__intersection__1 javax.crypto.spec.SecretKeySpec__<init>__2 java.lang.String__charAt__1 java.awt.Shape__getPathIterator__1 org.jfree.chart.plot.XYPlot__getRendererForDataset__1'
names='java.io.ObjectOutputStream__writeObject__1 java.lang.Long__parseLong__1 java.util.Map__get__1  java.util.List__get__1  java.util.StringTokenizer__nextToken__0 javax.crypto.Cipher__init__2 java.io.DataOutputStream__<init>__1 java.sql.PreparedStatement__execute*__0  java.util.Iterator__next__0 org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2 java.util.Scanner__next__0 com.itextpdf.text.pdf.PdfArray__getPdfObject__1 java.sql.ResultSet__next__0 org.apache.lucene.index.SegmentInfos__info__1 java.lang.Byte__parseByte__1 java.lang.Short__parseShort__1 java.util.Enumeration__nextElement__0 org.jfree.chart.plot.XYPlot__getRendererForDataset__1 org.jfree.chart.plot.PlotRenderingInfo__getOwner__0 org.jfree.chart.plot.CategoryPlot__getDataset__1 com.itextpdf.text.pdf.PdfDictionary__getAsString__1 java.nio.ByteBuffer__put__1 java.util.SortedMap__firstKey__0 org.kohsuke.args4j.spi.Parameters__getParameter__1 java.nio.channels.FileChannel__write__1  java.io.PrintWriter__write__1 javax.swing.JFrame__setVisible__1 java.util.Optional__get__0 java.lang.String__charAt__1 java.awt.Shape__getPathIterator__1'
projects='commons-bcel commons-math commons-text curator directory-fortress-core h2database itextpdf jackrabbit jfreechart pdfbox santuario-java swingx wildfly-elytron xmlgraphics-fop commons-lang bigtop'

args=("$@")

rm all_${args[0]}_instances.txt 

echo 'print to ' all_${args[0]}_instances.txt 
for name in $names
do
	echo '\t' $name
	for project in $projects
	do
		echo '\t\t' $project
		python ${args[0]}.py /Users/first_author/repos/MUDetect/src2egroum2aug/output/${project}_comb-external___$name/ $name >> all_${args[0]}_instances.txt || { echo 'failed' ; exit 1; }

	done
done 


echo 'printed to ' all_${args[0]}_instances.txt 
