package lab.app;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import lab.dto.ComplaintDTO;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        // Baza
        String status =
                client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" +
                                "api/complaints/1/status")
                        .request(MediaType.TEXT_PLAIN)
                        .get(String.class);
        System.out.println("Status: " + status);

        // Pobierz wszystkie skargi
        List<ComplaintDTO> complaints = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" +
                        "api/complaints")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<>() {
                });

        System.out.println("\nAll complaints:");
        for (ComplaintDTO complaint : complaints) {
            System.out.println(complaint.getComplaintText());
        }

        // Pobierz po id
        ComplaintDTO selectedComplaint = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" +
                        "api/complaints/1")
                .request(MediaType.APPLICATION_JSON)
                .get(ComplaintDTO.class);

        System.out.println("\nComplaint with id=1: " + selectedComplaint.getStatus());

        // Podmień status
        selectedComplaint.setStatus("closed");
        ComplaintDTO returnValue = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" +
                        "api/complaints/2")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(selectedComplaint), ComplaintDTO.class);

        // Pobierz filtrowane
        List<ComplaintDTO> allOpen = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" +
                        "api/complaints")
                .queryParam("status", "open")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<>() {
                });

        System.out.println("\nAll open complaints:");
        for (ComplaintDTO complaint : allOpen) {
            System.out.println(complaint.getId());
        }

        client.close();
    }
}
