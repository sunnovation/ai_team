package com.ai.data.config;

import com.ai.data.constant.TrainAIQueryConstant;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class DataLoader  {

    private final JdbcClient jdbcClient;

    private final VectorStore vectorStore;

    public DataLoader(JdbcClient jdbcClient,VectorStore vectorStore){
        this.jdbcClient=jdbcClient;
        this.vectorStore=vectorStore;
    }

    @Value("E:\\abc.pdf")
    private Resource pdfResource;



    @PostConstruct
    public void loadData(){
        Integer counter=jdbcClient.sql(TrainAIQueryConstant.COUT_QUERY).query(Integer.class).single();

        System.out.println("No of Records in the PG Vector Store = " + counter);
        if(counter==0){
            System.out.println("Loading Indian Constitution in the PG Vector Store");
            PdfDocumentReaderConfig config=PdfDocumentReaderConfig.builder()
                    .withPagesPerDocument(1)
                    .build();
            System.out.println(config.pagesPerDocument);
            PagePdfDocumentReader reader
                    = new PagePdfDocumentReader(pdfResource, config);

            var textSplitter = new TokenTextSplitter();
            vectorStore.accept(textSplitter.apply(reader.get()));
            System.out.println("Application is ready to Serve the Requests");

        }




    }


}
