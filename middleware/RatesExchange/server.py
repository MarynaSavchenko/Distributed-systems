# Copyright 2015 gRPC authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
"""The Python implementation of the GRPC helloworld.Greeter server."""
import random
from concurrent import futures
import time
import logging

import grpc

from currency_pb2 import Currency, Rate
from currency_pb2_grpc import CurrencyExchangeServicer, add_CurrencyExchangeServicer_to_server

_ONE_DAY_IN_SECONDS = 60 * 60 * 24


class CurrencyExchange(CurrencyExchangeServicer):

    def getRate(self, request, context):
        rates_eur = {
            Currency.Value('USD'): 1.12,
            Currency.Value('PLN'): 4.28,
            Currency.Value('EUR'): 1,
        }
        DIFF = 0.05
        while True:
            rate = None
            if request.currencyTo == Currency.Value('EUR'):
                rates_eur[request.currencyFrom] += random.uniform(-DIFF, DIFF)
                euro_amount = rates_eur[Currency.Value('EUR')]/rates_eur[request.currencyFrom]
                rate = Rate(value=euro_amount)
            else:
                rates_eur[request.currencyTo] += random.uniform(-DIFF, DIFF)
                amount = rates_eur[request.currencyTo]/rates_eur[request.currencyFrom]
                rate = Rate(value=amount)

            yield rate
            time.sleep(5)





def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    add_CurrencyExchangeServicer_to_server(CurrencyExchange(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Server running...")
    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == '__main__':
    logging.basicConfig()
    serve()
