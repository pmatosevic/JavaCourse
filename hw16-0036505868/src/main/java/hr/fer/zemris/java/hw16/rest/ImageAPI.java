package hr.fer.zemris.java.hw16.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.model.ImageInfoProvider;

/**
 * A REST API that provides information about images.
 * 
 * @author Patrik
 *
 */
@Path("/")
public class ImageAPI {

	/**
	 * Returns the list of all tags in JSON format
	 * @return response
	 */
	@Path("tag")
	@GET
	@Produces("application/json")
	public Response getTagsList() {
		Gson gson = new Gson();
		String json = gson.toJson(ImageInfoProvider.getProvider().getTags());
		
		return Response.status(Status.OK).entity(json).build();
	}

	/**
	 * Returns the list of all filenames for given tag in JSON format
	 * @param tag tag
	 * @return response
	 */
	@Path("tag/{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImagesForTag(@PathParam("tag") String tag) {
		Gson gson = new Gson();
		String json = gson.toJson(ImageInfoProvider.getProvider().getImagesByTag(tag));
		
		return Response.status(Status.OK).entity(json).build();
	}
	
	/**
	 * Returns the information about a single image in JSON format
	 * @param filename image filename
	 * @return response
	 */
	@Path("image/{filename}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImageInfo(@PathParam("filename") String filename) {
		Gson gson = new Gson();
		String json = gson.toJson(ImageInfoProvider.getProvider().getImageByFilename(filename));
		
		return Response.status(Status.OK).entity(json).build();
	}
	
}
