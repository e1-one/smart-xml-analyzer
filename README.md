# smart-xml-analyzer
Application that analyzes HTML and finds a specific element, even after changes, using a set of extracted attributes

###  Brief guide how to build
gradlew build fatJar

### Tool execution:
java -jar <your_bundled_app>.jar <input_origin_file_path> <input_other_sample_file_path> <id_of_target_element_that_needs_to_be_found>
##### Execution example:
java -jar ./build/libs/smart-xml-analyzer-1.0-SNAPSHOT.jar ./samples/sample-0-origin.html ./samples/sample-4-the-mash.html make-everything-ok-button
##### Comparison output for sample pages:
__input_origin_file_path = ./samples/sample-0-origin.html__
- input_other_sample_file_path = ./samples/sample-1-evil-gemini.html \

[INFO] 2019-03-10 20:35:07,285 c.a.SmartXmlAnalyzer - Element: \<a class="btn btn-success" href="#check-and-ok" title="Make-Button" rel="done" onclick="javascript:window.okDone(); return false;"\>\</a\>\
[INFO] 2019-03-10 20:35:07,288 c.a.SmartXmlAnalyzer - Element Attributes and the values of their contribution to the result\
[INFO] 2019-03-10 20:35:07,289 c.a.SmartXmlAnalyzer - onclick     with value: 1\
[INFO] 2019-03-10 20:35:07,289 c.a.SmartXmlAnalyzer - title     with value: 10\
[INFO] 2019-03-10 20:35:07,289 c.a.SmartXmlAnalyzer - class     with value: 7\
[INFO] 2019-03-10 20:35:07,289 c.a.SmartXmlAnalyzer - Total contribution score of this element is 18\
[INFO] 2019-03-10 20:35:07,290 c.a.Main - XML path to the element: /html[1]/body[2]/div[1]/div[5]/div[9]/div[1]/div[1]/div[5]/a[0]

- input_other_sample_file_path = ./samples/sample-3-the-escape.html

[INFO] 2019-03-11 20:10:37,702 c.a.SmartXmlAnalyzer - Element: \<a class="btn btn-success" href="#ok" title="Do-Link" rel="next" onclick="javascript:window.okDone(); return false;"\>\</a\> \
[INFO] 2019-03-10 20:10:37,703 c.a.Main - Element Attributes and the values of their contribution to the result\
[INFO] 2019-03-10 20:10:37,709 c.a.Main - onclick     with value: 1\
[INFO] 2019-03-10 20:10:37,709 c.a.Main - rel     with value: 3\
[INFO] 2019-03-10 20:10:37,709 c.a.Main - href     with value: 6\
[INFO] 2019-03-10 20:10:37,709 c.a.Main - class     with value: 7\
[INFO] 2019-03-10 20:10:37,710 c.a.Main - Total contribution score is 17\
[INFO] 2019-03-10 20:10:37,710 c.a.Main - XML path to the element: /html[1]/body[2]/div[1]/div[5]/div[9]/div[1]/div[1]/div[9]/a[0]
