package com.hw.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.hw.bean.Record;
import com.hw.other.DBUtils;
import com.hw.other.Page;

public class RecordDao {

	public int insert(Record record) {
		Connection conn = null;
		try {
			try {
				conn = DBUtils.getConn();
				String sql = "INSERT INTO receiver(method, header, takeType, dataType, dataJSON, dataStream, ext, time)"
							+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = 
						conn.prepareStatement(sql);
				ps.setString(1, record.getMethod());
				ps.setString(2, record.getHeader());
				ps.setInt(3, record.getTakeType());
				ps.setInt(4, record.getDataType());
				if(record.getDataJSON() != null) {
					ps.setString(5, record.getDataJSON());
				} else {
					ps.setNull(5, Types.VARCHAR);
				}
				if(record.getDataStream() != null) {
					ps.setBlob(6, new ByteArrayInputStream(record.getDataStream()));
				} else {
					ps.setNull(6, Types.BLOB);
				}
				ps.setString(7, record.getExt());
				ps.setDate(8, new Date(System.currentTimeMillis()));
				return ps.executeUpdate();
			} finally {
				if(conn != null) {
					conn.close();
				}
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public int update(Record record) {
		Connection conn = null;
		try {
			try {
				
				return 0;
			} finally {
				if(conn != null) {
					conn.close();
				}
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public int delete(int id) {
		Connection conn = null;
		try {
			try {
				conn = DBUtils.getConn();
				PreparedStatement ps = conn.prepareStatement("DELETE FROM receiver WHERE id = ?");
				ps.setInt(1, id);
				return ps.executeUpdate();
			} finally {
				if(conn != null) {
					conn.close();
				}
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Page<Record> query(int page, int size) {
		Connection conn = null;
		try {
			try {
				conn = DBUtils.getConn();
				String sql = "SELECT id, method, header, takeType, dataType, dataJSON, dataStream, ext, time "
						+ "FROM receiver ORDER BY time DESC LIMIT ?, ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, page * (size - 1));
				ps.setInt(2, page);
				ResultSet rs = ps.executeQuery();
				List<Record> list = new ArrayList<Record>();
				Record rec = null;
				Blob blob = null;
				InputStream in = null;
				ByteArrayOutputStream bos = null;
				int length = 0;
				byte[] buff = new byte[512];
				while(rs.next()) {
					rec = new Record();
					rec.setId(rs.getInt("id"));
					rec.setMethod(rs.getString("method"));
					rec.setHeader(rs.getString("header"));
					rec.setTakeType(rs.getInt("takeType"));
					rec.setDataType(rs.getInt("dataType"));
					rec.setDataJSON(rs.getString("dataJSON"));
					blob = rs.getBlob("dataStream");
					if(blob != null) {
						in = blob.getBinaryStream();
						bos = new ByteArrayOutputStream();
						while((length = in.read(buff)) != -1) {
							bos.write(buff, 0, length);
						}
						in.close();
						rec.setDataStream(bos.toByteArray());
					}
					rec.setTime(rs.getDate("time"));
					list.add(rec);
				}
				Page<Record> mode = new Page<Record>();
				mode.setCurrentPage(page);
				mode.setPageSize(size);
				mode.setData(list);
				return mode;
			} finally {
				if(conn != null) {
					conn.close();
				}
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
