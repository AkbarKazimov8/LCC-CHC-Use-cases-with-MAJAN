package dummy.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import dummy.Calculation;
import dummy.RDFGraph;
import dummy.ThriftAJANService;
import org.apache.http.HttpResponse;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleIRI;
import org.eclipse.rdf4j.model.impl.SimpleLiteral;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class DummyController {

    private final MediaType TURTLE = MediaType.parseMediaType("text/turtle");
    private final RestTemplate template = new RestTemplate();
    private boolean asyncAbort = false;

    private final IRI Reuqest_URI;
    private final IRI Is_Test_Predicate, Test_Subject, Test_Object;
    private final IRI Avatar;
    private final IRI Position;
    private final IRI At_Position;
    private final IRI Any_URI;
    private final IRI XSD_String;
    //MAJAN Stuff
    private final IRI WelcomeNamespace, AjanNamespace;
    private final IRI Agent_Object;
            
    private final ValueFactory factory;

    public DummyController() {
        factory = SimpleValueFactory.getInstance();
        Reuqest_URI = factory.createIRI("http://www.ajan.de/actn#asyncRequestURI");
        Is_Test_Predicate = factory.createIRI("http://test/predicate");
        Test_Subject = factory.createIRI("http://test/TestSubject");
        Test_Object = factory.createIRI("http://test/TestObject");
        Avatar = factory.createIRI("http://test/Avatar");
        Position = factory.createIRI("http://test/Position");
        At_Position = factory.createIRI("http://test/position");
        Any_URI = factory.createIRI("http://www.w3.org/2001/XMLSchema#anyURI");
        XSD_String = factory.createIRI("http://www.w3.org/2001/XMLSchema#string");
        WelcomeNamespace = factory.createIRI("http://localhost:8090/rdf4j/repositories/welcome_ontology#");
        AjanNamespace = factory.createIRI("http://www.ajan.de/ajan-ns#");
        Agent_Object = factory.createIRI(AjanNamespace + "Agent");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String hallo(String message) {
        System.out.println("Bonschorno!");
        return "Bonschorno!";
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/resource"
    )
    public String resource() {
        int randomNum = 60 + (int) (Math.random() * 150);
        System.out.println("resource/GET");
        return "<http://localhost:3030/Andre> <http://www.dfki.de/inversiv-ns#gewicht> '" + randomNum + "'^^xsd:int .";
    }
    
        @RequestMapping(
            method = RequestMethod.POST,
            value = "/resourcePost"
    )
    public String resourcePost(@RequestBody String body) {
        System.out.println("resource/Post:" + body);

        int randomNum = 60 + (int) (Math.random() * 150);
        return "<http://localhost:3030/Andre> <http://www.dfki.de/inversiv-ns#gewicht> '" + randomNum + "'^^xsd:int .";
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api",
            produces = "application/json"
    )
    public String api(@RequestParam("action") String action, @RequestParam("token") int token) throws IOException, URISyntaxException {
        if (action.equals("getTaskList") && token == 123) {
            return "{\n"
                    + "\"tasks\": [\n"
                    + "   {\n"
                    + "     \"step\": 1,\n"
                    + "     \"operation\": \"TightenLoose\",\n"
                    + "     \"part\": {\n"
                    + "         \"type\": \"ScrewRightRearMudguard\",\n"
                    + "         \"id\": \"NULL\"\n"
                    + "      },\n"
                    + "      \"tool\": {\n"
                    + "         \"type\": \"LeftHand\",\n"
                    + "         \"id\": \"NULL\"\n"
                    + "      },\n"
                    + "      \"position\": {\n"
                    + "         \"type\": \"\",\n"
                    + "         \"id\": \"NULL\"\n"
                    + "     }\n"
                    + "    }\n"
                    + "  ]\n"
                    + "}";
        } else {
            return "{}";
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/thrift"
    )
    public String thrift() {
        System.out.println("Start Thrift stuff");
        try {
            TTransport transport;
            transport = new THttpClient("http://localhost:8080/thrift");
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            ThriftAJANService.Client client = new ThriftAJANService.Client(protocol);
            int randomNum = 60 + (int) (Math.random() * 150);
            RDFGraph knowledge = new RDFGraph();
            knowledge.format = "text/turtle";
            knowledge.graph = "<http://localhost:3030/Andre> <http://www.dfki.de/inversiv-ns#gewicht> '" + randomNum + "'^^xsd:int .";
            client.createAgent("ThriftAgent", "http://localhost:8090/rdf4j/repositories/agents#MOSIMAgent", knowledge);
            System.out.println();
            transport.close();
        } catch (TException ex) {
            System.out.println(ex);
        }

        return "Thrift!";
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/thirftPluginAction", consumes = "application/json"
    )
    public void thriftCalculation(@RequestBody Calculation calc) {
        int ans;
        switch (calc.getFunc()) {
            case "add":
                ans = calc.getOp1() + calc.getOp2();
                break;
            case "sub":
                ans = calc.getOp1() - calc.getOp2();
                break;
            case "mul":
                ans = calc.getOp1() * calc.getOp2();
                break;
            case "div":
                if (calc.getOp2() == 0) {
                    ans = 0;
                } else {
                    ans = calc.getOp1() / calc.getOp2();
                }
                break;
            default:
                ans = -1;
                break;
        }
        sendAnswer(ans, calc.getActionID(), calc.getAjanHost(), calc.getAjanPort());
    }

    void sendAnswer(int answer, String actionID, String ajanHost, int ajanPort) {
        System.out.println("Sending answer : " + answer + " ajan Host : " + ajanHost + " ajan Port : " + ajanPort + " actionID : " + actionID);
        int result;
        try {
            TTransport transport;
            transport = new TSocket(ajanHost, ajanPort);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            ThriftAJANService.Client client = new ThriftAJANService.Client(protocol);
            client.receiveAsync(actionID, answer);

            transport.close();
        } catch (TException x) {
            x.printStackTrace();
            System.out.println("Exception found while calculation.");
        }

    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/sync",
            consumes = "text/turtle",
            produces = "text/turtle"
    )
    public String sync(@RequestBody String request) throws IOException, URISyntaxException {
        return this.response(readRequest(request, RDFFormat.TURTLE), RDFFormat.TURTLE);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/dms/receiveDIP",
            consumes = "application/ld+json",
            produces = "application/ld+json"
    )
    public String sync_json_ld(@RequestBody String request) throws IOException, URISyntaxException {

        System.out.println("DMS receiveDIP-------------Input Start-------------");
        System.out.println(request);
        System.out.println("DMS receiveDIP-------------Input End-------------");
//        Model model = readRequest(request, RDFFormat.JSONLD);
//        System.out.println("======================");
//        printModel(model);
//        System.out.println("======================");

        return request;
//        return this.response(model, RDFFormat.JSONLD);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/kms/receiveDIP",
            consumes = "text/turtle",
            produces = "text/turtle"
    )
    public String kms_receiveDIP(@RequestBody String request) throws IOException, URISyntaxException {

        System.out.println("KMS receiveDIP-------------Input Start-------------");
        System.out.println(request);
        System.out.println("KMS receiveDIP-------------Input End-------------");
//        Model model = readRequest(request, RDFFormat.JSONLD);
//        System.out.println("======================");
//        printModel(model);
//        System.out.println("======================");

        return request;
//        return this.response(model, RDFFormat.JSONLD);
    }
    private static void printModel(Model model) {
        Iterator<Statement> statementIterator = model.iterator();
        while (statementIterator.hasNext()) {
            System.out.println(statementIterator.next());
        }

    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/dispatcher/receiveProfile",
            consumes = "application/ld+json",
            produces = "application/ld+json"
    )
    public String user_profile(@RequestBody String request,
                               @RequestHeader(value = "To") String header) throws IOException, URISyntaxException {
        System.out.println("Dispatcher receiveProfile-------------Input Start-------------");
        System.out.println(request);
        System.out.println("Dispatcher receiveProfile-------------Input End-------------");
        return this.response(readRequest(request, RDFFormat.JSONLD), RDFFormat.JSONLD);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/dispatcher/activeAgents",
            produces = "text/turtle"
    )
    public String activeAgents() throws IOException, URISyntaxException {
        System.out.println("---------active agents request-----");
        String activeAgents = "@prefix welcome: <http://welcome/services/1.0/> .\n" +
                "welcome:Agent1 welcome:is welcome:active.\n" +
                "welcome:Agent2 welcome:is welcome:inactive.\n" +
                "welcome:Agent3 welcome:is welcome:inactive.\n" +
                "welcome:Agent4 welcome:is welcome:in_coordination.";
        return activeAgents;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/dispatcher/translationForm",
            produces = "text/turtle"
    )
    public String translationForm() throws IOException, URISyntaxException {
        System.out.println("----translation form request -----");
        String translationForm = "@prefix welcome: <http://www.semanticweb.org/welcome#> .\n" +
                "welcome:translationForm welcome:is welcome:empty.\n";
        return translationForm;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/walk",
            consumes = "text/turtle"
    )
    public String walk(@RequestBody String request) throws IOException {
        asyncAbort = false;
        new Thread(() -> {
            try {
                Random rand = new Random();
                Thread.sleep(rand.nextInt(4500) + 500);
                if (!asyncAbort) {
                    this.walkResponse(readRequest(request, RDFFormat.TURTLE));
                }
            } catch (IOException | URISyntaxException | InterruptedException ex) {
                Logger.getLogger(DummyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        return "";
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/async",
            consumes = "text/turtle"
    )
    public String async(@RequestBody String request) throws IOException {
        asyncAbort = false;
        new Thread(() -> {
            try {
                Random rand = new Random();
                Thread.sleep(rand.nextInt(4500) + 500);
                if (!asyncAbort) {
                    this.asyncResponse(readRequest(request, RDFFormat.TURTLE));
                }
            } catch (IOException | URISyntaxException | InterruptedException ex) {
                Logger.getLogger(DummyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        return "";
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/async/abort",
            consumes = "text/turtle"
    )
    public String asyncAbort(@RequestBody String request) throws IOException {
        asyncAbort = true;
        return async(request);
    }

    private void walkResponse(Model model) throws URISyntaxException {
        for (Value uriLiteral : model.filter(null, Reuqest_URI, null).objects()) {
            URI requestURI = new URI(getLiteralString(uriLiteral));
            sendResponse(requestURI, getAtTarget(model, RDFFormat.TURTLE));
        }
    }

    private String getAtTarget(Model model, RDFFormat mimeType) {
        Resource avatar = Avatar;
        Resource current = Position;
        Resource target = Position;
        for (Resource resource : model.filter(null, null, Avatar).subjects()) {
            avatar = resource;
        }
        for (Value literal : model.filter(avatar, At_Position, null).objects()) {
            if (literal instanceof SimpleIRI) {
                current = ((SimpleIRI) literal);
                target = current;
            }
        }
        for (Resource resource : model.filter(null, null, Position).subjects()) {
            if (!resource.equals(current)) {
                target = resource;
            }
        }
        ModelBuilder builder = new ModelBuilder();
        builder.subject(avatar).add(At_Position, target);
        OutputStream response = new ByteArrayOutputStream();
        Rio.write(builder.build(), response, mimeType);
        System.out.println(response.toString());
        return response.toString();
    }

    private void asyncResponse(Model model) throws URISyntaxException {
        for (Value uriLiteral : model.filter(null, Reuqest_URI, null).objects()) {
            URI requestURI = new URI(getLiteralString(uriLiteral));
            sendResponse(requestURI, response(model, RDFFormat.TURTLE));
        }
    }

    private String response(Model model, RDFFormat mimeType) throws URISyntaxException {
        String text = "Bonschorno!";
        for (Value string : model.filter(null, Is_Test_Predicate, null).objects()) {
            text = getLiteralString(string);
        }
       // System.out.println("text = " + text);
        ModelBuilder builder = new ModelBuilder();
        builder.subject("http://test/Subject").add(Is_Test_Predicate, text);
        OutputStream response = new ByteArrayOutputStream();
        //System.out.println("builder.build()  = " + builder.build());

       // System.out.println("mimeType = " + mimeType);
        //System.out.println("response = " + response);
        
        Rio.write(builder.build(), response, mimeType);
        //System.out.println("-------------Response-------------");
       // System.out.println(response.toString());
        //System.out.println("-------------Response-------------");
        return response.toString();
    }

    private String getLiteralString(Value literal) throws URISyntaxException {
        String string = "";
        if (literal instanceof SimpleIRI) {
            string = ((SimpleIRI) literal).stringValue();
        } else if (((SimpleLiteral) literal).getDatatype().equals(Any_URI)) {
            SimpleLiteral lit = (SimpleLiteral) literal;
            string = lit.getLabel();
        } else if (((SimpleLiteral) literal).getDatatype().equals(XSD_String)) {
            SimpleLiteral lit = (SimpleLiteral) literal;
            string = lit.getLabel();
        }
        return string;
    }

    private Model readRequest(String request, RDFFormat format) throws IOException {
//        System.out.println("Request:");
//        System.out.println(request);
        InputStream input = new ByteArrayInputStream(request.getBytes());
        Model model = Rio.parse(input, "", format);
        return model;
    }

    private void sendResponse(URI requestURI, String requestBody) {
        System.out.println("Response:");
        System.out.println(requestBody);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(TURTLE);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        template.postForObject(requestURI, entity, String.class);
    }
    
    
    
    
    // ------------------------MAJAN Stuff--------------------------
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/teacherPanel/inform",
            consumes = "text/turtle",
            produces = "text/turtle"
    )
    public String informTeacher(@RequestBody String request) throws IOException, URISyntaxException {
        System.out.println("Teacher Panel received info");
        System.out.println("Info is:\n "+request);
        String response = this.response(readRequest(request, RDFFormat.TURTLE), RDFFormat.TURTLE);
        //System.out.println("Teacher Panel responded as following: "+response);
        return response;
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/app/inform",
            consumes = "application/ld+json",
            produces = "application/ld+json"
    )
    public String informApp(@RequestBody String request) throws IOException, URISyntaxException {
        System.out.println("App received info from agent");
        System.out.println("Info is: "+request);
        String response = this.response(readRequest(request, RDFFormat.JSONLD), RDFFormat.JSONLD);
        System.out.println("App responded as following: "+response);
        return response;
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/app/ask",
            consumes = "application/ld+json",
            produces = "application/ld+json"
    )
    public String askApp(@RequestBody String request) throws IOException, URISyntaxException {
        System.out.println("App received request from agent");
        System.out.println("Request is: "+request);
        String response = this.responseMajan(this.responseStartCHC(readRequest(request, RDFFormat.JSONLD), true), RDFFormat.JSONLD);
        System.out.println("App responded as following: "+response);
        
        return response;
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/wpm/updateAgentStatus",
            consumes = "application/ld+json",
            produces = "application/ld+json"
    )
    public String agentStatusWPM(@RequestBody String request) throws IOException, URISyntaxException {
        System.out.println("WPM received status update from agent");
        System.out.println("Message is: "+request);
        String response = this.responseMajan(readRequest(request, RDFFormat.JSONLD), RDFFormat.JSONLD);
        System.out.println("WPM responded as following: "+response);
        
        return response;
    }
    
    
     private String responseMajan(Model model, RDFFormat mimeType) throws URISyntaxException {
        if(model.isEmpty()){
            ModelBuilder builder = new ModelBuilder();
            builder.subject(Test_Subject).add(Is_Test_Predicate, Test_Object);
            model=builder.build();
        }
        
        OutputStream response = new ByteArrayOutputStream();
        Rio.write(model, response, mimeType);
        return response.toString();
    }
     
     private Model responseStartCHC(Model model, boolean startCHCValue) throws URISyntaxException {
         Resource subject;
         if(!model.contains(null, RDF.TYPE, Agent_Object)){
             subject = factory.createIRI(AjanNamespace + "agentZero");
         }else{
             subject = model.filter(null, RDF.TYPE, Agent_Object).subjects().iterator().next();
         }
         ModelBuilder builder = new ModelBuilder();
         IRI startCHCPred = factory.createIRI(WelcomeNamespace + "startCHC");
         builder.subject(subject).add(startCHCPred, startCHCValue);
         model=builder.build();
         
        return model;
    }
    
     
}
