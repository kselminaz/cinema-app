package group.aist.cinemaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailDto {

   private String toMailAddress;

   private String subject;

   private String content;

   private String fileName;


}
