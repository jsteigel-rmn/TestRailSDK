package com.rmn.testrail.service;

import com.rmn.testrail.entity.*;
import com.rmn.testrail.util.MockHTTPUtils;
import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TestRailServiceTest {
    private TestRailService getTestRailsEntities(String fileName) {
        MockHTTPUtils utils = new MockHTTPUtils();
        TestRailService service = new TestRailService();
        service.setHttpUtils( utils );

        String contents = getFileContents(fileName);
        utils.setContentsFromConnection(contents);
        return service;
    }

    @Test
    public void testGetProjects() {
        TestRailService service = getTestRailsEntities("Projects.json");
        List<Project> projects = service.getProjects();
        Assert.assertEquals("RetailMeNot", projects.get(0).getName());
    }

    @Test
    public void testGetTestSuites() {
        TestRailService service = getTestRailsEntities("TestSuites.json");
        List<TestSuite> suites = service.getTestSuites(0);
        Assert.assertEquals("Footer", suites.get(0).getName());
    }

    @Test
    public void testGetSections() {
        TestRailService service = getTestRailsEntities("Sections.json");
        List<Section> sections = service.getSections(0, 0);
        Assert.assertEquals("All Test Cases",  sections.get(0).getName());
    }

    @Test
    public void testGetTestCases() {
        TestRailService service = getTestRailsEntities("TestCases.json");
        List<TestCase> testCases = service.getTestCases(0, 0);
        Assert.assertEquals("Test Case",  testCases.get(0).getTitle());
    }

    @Test
    public void testGetTestRuns() {
        TestRailService service = getTestRailsEntities("TestRuns.json");
        List<TestRun> testRuns = service.getTestRuns(0);
        Assert.assertEquals("Test Suite", testRuns.get(0).getName());
    }

    @Test
    public void testGetTestPlans() {
        TestRailService service = getTestRailsEntities("TestPlans.json");
        List<TestPlan> testPlans = service.getTestPlans(0);
        Assert.assertEquals("Test Plan 2",  testPlans.get(0).getName());
    }

    /**
     * Returns the contents of the given file as a List of Strings
     * @param filename The name of the file to read. This file must be on the classpath at runtime
     * @return
     */
    public static String getFileContents( String filename ) {
        InputStream stream = TestRailServiceTest.class.getClassLoader().getResourceAsStream( filename );

        StringBuilder contents = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String strLine;
            while ((strLine = br.readLine()) != null )   {
                contents.append(strLine);
            }
            stream.close();
        } catch (IOException ex) {
            throw new RuntimeException("Could not read file: " + filename);
        }
        return contents.toString();
    }
}
