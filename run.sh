echo "Creating Customer $i"
curl -w "\n" -X POST http://localhost:8000/customer/10

echo "Creating Product $i"
curl -w "\n" -X POST http://localhost:8000/product/10

echo "Creating Order"
for i in {1..400}
do
   curl -w "Orders Created\n" -X POST http://localhost:8000/sellOrder/500
done

