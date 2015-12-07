'use strict';

describe('PersoonEvent Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPersoonEvent, MockEventScore, MockEvent, MockPersoon, MockComment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPersoonEvent = jasmine.createSpy('MockPersoonEvent');
        MockEventScore = jasmine.createSpy('MockEventScore');
        MockEvent = jasmine.createSpy('MockEvent');
        MockPersoon = jasmine.createSpy('MockPersoon');
        MockComment = jasmine.createSpy('MockComment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PersoonEvent': MockPersoonEvent,
            'EventScore': MockEventScore,
            'Event': MockEvent,
            'Persoon': MockPersoon,
            'Comment': MockComment
        };
        createController = function() {
            $injector.get('$controller')("PersoonEventDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'tiimspotApp:persoonEventUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
