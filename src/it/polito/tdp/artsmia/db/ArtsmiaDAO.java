package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Link;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects(Map<Integer, ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				if (!idMap.containsKey(res.getInt("object_id"))) {
					ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
							res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
							res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
							res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
					
					result.add(artObj);
					idMap.put(res.getInt("object_id"), artObj);
				} else {
					result.add(idMap.get(res.getInt("object_id")));
				}
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Link> listLinks() {
		String sql = "SELECT eo1.object_id as e1, eo2.object_id as e2, count(*) as cnt FROM exhibition_objects as eo1, exhibition_objects as eo2 " +
						"WHERE eo1.exhibition_id = eo2.exhibition_id AND eo1.object_id > eo2.object_id " +
						"GROUP BY eo1.object_id, eo2.object_id; ";
		List<Link> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Link l = new Link(res.getInt("e1"), res.getInt("e2"), res.getInt("cnt"));
				result.add(l);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
