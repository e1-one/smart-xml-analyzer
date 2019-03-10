# smart-xml-analyzer
Application that analyzes HTML and finds a specific element, even after changes, using a set of extracted attributes

###  Brief guide how to build
gradlew build fatJar

### Tool execution:
java -jar <your_bundled_app>.jar <input_origin_file_path> <input_other_sample_file_path> <target_element_that_needs_to_be_found>
##### Example:
java -jar ./build/libs/smart-xml-analyzer-1.0-SNAPSHOT.jar ./samples/sample-0-origin.html ./samples/sample-4-the-mash.html make-everything-ok-button
#####Output:
/html[1]/body[2]/div[1]/div[5]/div[9]/div[1]/div[1]/div[5]/a[0]